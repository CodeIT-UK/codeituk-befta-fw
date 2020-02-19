package uk.gov.hmcts.befta.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BeftaUtils {

    public static File getClassPathResourceIntoTemporaryFile(String resourcePath) {
        try {
            InputStream stream = BeftaUtils.class.getClassLoader().getResource(resourcePath).openStream();
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            File tempFile = new File("_temp_" + System.currentTimeMillis() + ".temp");
            OutputStream outStream = new FileOutputStream(tempFile);
            outStream.write(buffer);
            outStream.close();
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}