package core.utilities;
import org.apache.log4j.Level;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {

    public static Properties readPropertiesFile(String filePath) {
        loggerSetup Logger = new loggerSetup();
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            Logger.writerLogger(Level.INFO, FileUtils.class.toString(), "Error, The file don't exist");
            properties = null;
        } catch (IOException e) {
            Logger.writerLogger(Level.INFO, FileUtils.class.toString(), "Error, can't read file");
            properties = null;
        }
        return properties;
    }
}

