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

    public static String ping() throws Exception{
        String delay = "";
        long pingStop = 0;
        long pingStart = System.currentTimeMillis();
//        InetAddress inet = InetAddress.getByName(ipAddress);
//
//        if (inet.isReachable(5000)){
//            pingStop = System.currentTimeMillis();
//            delay = Long.toString(pingStop - pingStart);
//        } else {
//            delay = "-";
//        }

//        System.out.println("DELAY: " + delay + "ms");

        InputStream is = Runtime.getRuntime().exec("ping orion.jobstreet.com -n 1").getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buff = new BufferedReader (isr);

        String line;
        StringBuilder line2 = new StringBuilder();
        while((line = buff.readLine()) != null){
            line2.append(line + "\n");
            System.out.print(line + "\n");
        }
        Thread.sleep(1000);
        return delay;
    }

    public static void setHostAddress(String hostAddress) throws Exception{
        ipAddress = hostAddress;
    }
}
