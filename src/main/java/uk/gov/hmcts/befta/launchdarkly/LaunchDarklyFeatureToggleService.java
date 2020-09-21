package uk.gov.hmcts.befta.launchdarkly;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.LDClient;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.befta.featureToggle.FeatureToggle;
import uk.gov.hmcts.befta.util.BeftaUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class LaunchDarklyFeatureToggleService implements FeatureToggle {

    private final Logger logger = LoggerFactory.getLogger(LaunchDarklyFeatureToggleService.class);

    public static final LaunchDarklyFeatureToggleService INSTANCE =
            new LaunchDarklyFeatureToggleService();

    public static final String BEFTA = "befta";
    public static final String USER = "user";
    public static final String SERVICENAME = "servicename";

    private static final String LAUNCH_DARKLY_FLAG = "FeatureToggle";
    private final LDClient ldClient = LaunchDarklyConfig.getLdInstance();

    @Override
    public void evaluateFlag(Scenario scenario) {

        boolean isLDFlagEnabled = true;
        logger.info("Inside evaluateFlag");
        scenario.log("Inside evaluateFlag");
        List<String> flagNames = scenario.getSourceTagNames().stream()
                .filter(tag -> tag.contains(LAUNCH_DARKLY_FLAG))
                .map(tag -> tag.substring(tag.indexOf("(") + 1, tag.indexOf(")")))
                .collect(Collectors.toList());

        if (ldClient != null && !flagNames.isEmpty()) {
            if (LaunchDarklyConfig.getLDMicroserviceName() == null) {
                BeftaUtils.skipScenario(scenario, ("The Scenario is being skipped as MICROSERVICE_NAME variable is not configured"));

            }
            if (LaunchDarklyConfig.getEnvironmentName() == null) {
                BeftaUtils.skipScenario(scenario, ("The Scenario is being skipped as LAUNCH_DARKLY_ENV is not configured"));
            }

            LDUser user = new LDUser.Builder(LaunchDarklyConfig.getEnvironmentName())
                    .firstName(BEFTA)
                    .lastName(USER)
                    .custom(SERVICENAME, LaunchDarklyConfig.getLDMicroserviceName())
                    .build();

            for (String flag : flagNames) {
                isLDFlagEnabled = ldClient.boolVariation(flag, user, false);

                if (!isLDFlagEnabled) {
                    Optional<String> scenarioName = scenario.getSourceTagNames().stream()
                            .filter(tag -> tag.contains("@S-"))
                            .map(tag -> tag.substring(1))
                            .findFirst();

                    BeftaUtils.skipScenario(scenario, String.format("The Scenario %s is being skipped as LD flag %s is disabled",
                            scenarioName.orElse(StringUtils.EMPTY), flag));
                    break;
                }

            }

        }
    }
}

