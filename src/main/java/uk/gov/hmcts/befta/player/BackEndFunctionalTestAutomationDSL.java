package uk.gov.hmcts.befta.player;

import java.io.IOException;

public interface BackEndFunctionalTestAutomationDSL {

    // DSL Element:
    // "an appropriate test context as detailed in the test data source"
    void initializeAppropriateTestContextAsDetailedInTheTestDataSource();

    // DSL Element:
    // "a case that has just been created as in [<some test data unique id>]"
    void createCaseWithTheDataProvidedInATestDataObject(String caseDataId) throws IOException;

    // DSL Element:
    // "a user with [<some specification about user data>]"
    void verifyThatThereIsAUserInTheContextWithAParticularSpecification(String specificationAboutAUser);

    // DSL Elements:
    // "[<some spec about the scenario context>] in the context of the scenario"
    // "[<some spec about the scenario context>] in the context"
    void verifyThatThereIsASpecificationAboutScenarioContext(String specificationAboutScenarioContext);

    // DSL Element:
    // "a request is prepared with appropriate values"
    void prepareARequestWithAppropriateValues() throws IOException;

    // DSL Element:
    // "the request [<some specification about request data>]"
    void verifyTheRequestInTheContextWithAParticularSpecification(String requestSpecification);

    // DSL Element:
    // "it is submitted to call the [<some operation name>] operation of [<some application / api product name>]"
    void submitTheRequestToCallAnOperationOfAProduct(String operation, String productName) throws IOException;

    // DSL Element:
    // "a positive response is received"
    void verifyThatAPositiveResponseWasReceived();

    // DSL Element:
    // "a negative response is received"
    void verifyThatANegativeResponseWasReceived();

    // DSL Element:
    // "the response has all the details as expected"
    void verifyThatTheResponseHasAllTheDetailsAsExpected() throws IOException;

    // DSL Element:
    // "the response [<some specification about response data>]"
    void verifyTheResponseInTheContextWithAParticularSpecification(String responseSpecification);

    // And a successful call [<some spec about the overall call data>] as in [<some test data unique id>],
    // And another successful call [<some spec about the overall call data>] as in [<some test data unique id>],
    //
    // a call [<some spec about the overall call data>] will get the expected response as in [<some test data unique id>]
    // another call [<some spec about the overall call data>] will get the expected response as in [<some test data unique id>]
    void performAndVerifyTheExpectedResponseForAnApiCall(String testDataSpec, String testDataId) throws IOException;
}
