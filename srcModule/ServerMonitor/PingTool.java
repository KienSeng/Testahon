package ServerMonitor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Created by kienseng.koh on 3/24/2016.
 */
public class PingTool {
    static String ipAddress;

    public static int ping() throws Exception{
        InputStream is = Runtime.getRuntime().exec("ping " + ipAddress + " -n 1").getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buff = new BufferedReader (isr);

        String line;
        StringBuilder line2 = new StringBuilder();
        while((line = buff.readLine()) != null){
            line2.append(line + "\n");
        }

        String[] splittedString = line2.toString().split(" ");
        int averagePing = Integer.parseInt(splittedString[splittedString.length - 1].replace("ms", "").trim());

        return averagePing;
    }

    public static void setHostAddress(String hostAddress) throws Exception{
        ipAddress = hostAddress;
    }
}
