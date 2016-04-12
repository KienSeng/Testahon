package Global;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class Global {
    public static Connection dbConnection;
    public static String propertyFileLocation;
    public static String serverPropertyFileLocation;
    public static HashMap<String, String> propertyMap = new HashMap<>();

    public static Double radioButtonWidth = 100.0;
    public static Double standardLabelWidth = 150.0;
    public static Double standardTextBoxWidth = 200.0;
    public static Double standardButtonWidth = 80.0;
}
