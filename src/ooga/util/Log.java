package ooga.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private Logger logger;
    FileHandler fh;

    public Log(String fName, String className) throws IOException {
        File f = new File(fName);
        if (!f.exists()) {
            f.createNewFile();
        }

        fh = new FileHandler(fName, true);
        logger = Logger.getLogger(className);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public Logger getLogger() {
        return logger;
    }
}
