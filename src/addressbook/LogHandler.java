package addressbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogHandler {

    private final static Logger logger = Logger.getLogger(LogHandler.class.getName());

    public static void setupLogging() {
        String loggingFilePath = "logging.properties";
        try (FileInputStream fileInputStream = new FileInputStream(loggingFilePath)) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Something wrong occurred", e);
            throw new RuntimeException("Could not load log properties.", e);
        }
    }

}
