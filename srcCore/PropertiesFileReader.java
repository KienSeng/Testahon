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


    public void loadAllPropertyToMap() throws Exception {
        propertyFile = new FileInputStream("config.properties");
        prop.load(propertyFile);
        Set<Object> keys = prop.keySet();

        for (Object k : keys) {
            String key = (String) k;
            String value = prop.getProperty(key);

            Global.propertyMap.put(key, value);
        }
    }

    //For internal use only
    private String readFromPropertyFile(String key) throws Exception {
        propertyFile = new FileInputStream("config.properties");

        String data = prop.getProperty(key);
        Logger.write("readFromPropertyFile(): " + data);

        return data;
    }

    private void closeFile() throws Exception {

    }
}
