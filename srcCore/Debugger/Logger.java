package Debugger;

import sun.reflect.annotation.ExceptionProxy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class Logger {
    private static boolean debugMode = false;

    private static String getDate() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    public static void write(String message) throws Exception {
        if (debugMode) {
            System.out.println("DEBUG LOG(" + getDate() + "): " + message);
        }
    }

    public static void debugMode(boolean toggle) throws Exception {
        debugMode = toggle;
    }

    public static void loopMap(String mapName, HashMap<String, String> map) throws Exception{
        write("================ Content of " + mapName + " ================");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            write(entry.getKey() + " : " + entry.getValue());
        }
    }
}
