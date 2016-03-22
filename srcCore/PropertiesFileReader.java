
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class PropertiesFileReader {
    Properties prop = new Properties();
    InputStream  propertyFile = null;


    public String loadProperty(String key) throws Exception{
        
    }

    //For internal use only
    private String readFromPropertyFile(String key) throws Exception{
        propertyFile = new FileInputStream("config.properties");

        String data = prop.getProperty(key);
        Logger.write("readFromPropertyFile(): " + data);

        return data;
    }

    private void closeFile() throws Exception{

    }
}
