package Global;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class Global {
    public static Connection dbConnection;
    public static String propertyFileLocation;
    public static HashMap<String, String> propertyMap = new HashMap<>();
}
