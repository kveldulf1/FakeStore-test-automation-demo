package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class FileReader {

    Properties properties;

    protected FileReader (String fileLocation){
        loadFile(fileLocation);
        loadData();
    }

    public void loadFile(String fileLocation) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There is something wrong with the file. Does it exist? File location: " + fileLocation);
        }
    }

    abstract void loadData();
}
