package PropertiesFile;

import Debugger.Logger;
import Global.Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class PropertiesFileReader {
    Properties prop = new Properties();
    InputStream propertyFile = null;
    String filePath;

    public void loadAllPropertyToMap() throws Exception {
        propertyFile = new FileInputStream(filePath);
        prop.load(propertyFile);

        Set<Object> keys = prop.keySet();

        for (Object k : keys) {
            String key = (String) k;
            String value = prop.getProperty(key);
            System.out.println(key + ":" + value);
            Global.propertyMap.put(key, value);
        }

        //Close file after load property file to prevent file lock
        closeFile();
    }

    //For internal use only
    private String readFromPropertyFile(String key) throws Exception {
        propertyFile = new FileInputStream("config.properties");

        String data = prop.getProperty(key);
        Logger.write("readFromPropertyFile(): " + data);

        return data;
    }

    public void setFile(String path) throws Exception{
        filePath = path;
    }

    private void closeFile() throws Exception {
        propertyFile.close();
    }

    public void refreshPropertyMapFromFile() throws Exception{
        if(!Global.propertyMap.isEmpty()){
            Global.propertyMap.clear();
        }

        loadAllPropertyToMap();
    }
}
