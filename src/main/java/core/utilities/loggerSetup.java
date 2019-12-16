package core.utilities;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class loggerSetup {

    /**
     * Set the Logger class of the log4j library
     *
     * @param className String
     * @return Logger
     */
    private Logger setClassLogger(String className) {
        return Logger.getLogger(className);
    }

    /**
     * Write the log in the console
     *
     * @param loggerType Level (INFO, ERROR, DEBUG, etc...)
     * @param className  String
     * @param message    String
     */
    public void writerLogger(Level loggerType, String className, String message) {
        setClassLogger(className).log(loggerType, message);
    }

}
