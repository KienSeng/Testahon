package PropertiesFile;

import Debugger.Logger;
import Global.Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class PropertiesFileReader {
    Properties prop = new Properties();
    InputStream propertyFile = null;
    String filePath;

    public void loadAllPropertyToMap() throws Exception {
        propertyFile = ClassLoader.getSystemResourceAsStream(filePath);
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

    public String readFromPropertyFile(String filePath, String key) throws Exception {
        propertyFile = new FileInputStream(filePath);
        prop.load(propertyFile);

        String data = prop.getProperty(key);
        closeFile();

        Logger.write("readFromPropertyFile(): " + data);

        return data;
    }

    private HashMap<String, String> getAllFromPropertyFile (String filePath) throws Exception{
        HashMap<String, String> propertyMap = new HashMap<>();

        propertyFile = new FileInputStream(filePath);
        prop.load(propertyFile);

        Set<Object> keys = prop.keySet();

        for (Object k : keys) {
            String key = (String) k;
            String value = prop.getProperty(key);
            propertyMap.put(key, value);
        }

        closeFile();

        return propertyMap;
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

    public static HashMap<String, String> returnAllValueFromPropertyMap() throws Exception{
        HashMap<String, String> mapToReturn = new HashMap<>();
        for (Map.Entry<?, ?> entry : Global.propertyMap.entrySet()) {
            mapToReturn.put(entry.getKey().toString(), entry.getValue().toString());
        }

        return mapToReturn;
    }
}
