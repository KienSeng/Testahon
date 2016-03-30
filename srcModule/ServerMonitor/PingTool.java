package ServerMonitor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Created by kienseng.koh on 3/24/2016.
 */
public class PingTool {

    public String[] ping(String serverAddress) throws Exception{
        InputStream is = Runtime.getRuntime().exec("ping " + serverAddress + " -n 1").getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buff = new BufferedReader (isr);

        String line;
        StringBuilder line2 = new StringBuilder();
        while((line = buff.readLine()) != null){
            line2.append(line + "\n");
        }

        String[] splittedString = line2.toString().split(" ");
        int averagePing = Integer.parseInt(splittedString[splittedString.length - 1].replace("ms", "").trim());

        String[] strToReturn = new String[2];
        strToReturn[0] = serverAddress;
        strToReturn[1] = String.valueOf(averagePing);

        return strToReturn;
    }
}
