package PropertiesFile;

import Debugger.Logger;
import Global.Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
    private String readFromPropertyFile(String filePath, String key) throws Exception {
        propertyFile = new FileInputStream(filePath);

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

    public static HashMap<String, String> returnAllValueFromPropertyMap() throws Exception{
        HashMap<String, String> mapToReturn = new HashMap<>();
        for (Map.Entry<?, ?> entry : Global.propertyMap.entrySet()) {
            mapToReturn.put(entry.getKey().toString(), entry.getValue().toString());
        }

        return mapToReturn;
    }
}
