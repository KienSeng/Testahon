package ServerMonitor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kienseng.koh on 4/01/2016.
 */
public class ServiceCheck {

    public boolean check(String serverAddress, String serviceName) throws Exception{

        System.out.println("sc \\\\" + serverAddress + " query \"" + serviceName + "\"");
        InputStream is = Runtime.getRuntime().exec("sc \\\\" + serverAddress + " query \"" + serviceName + "\"").getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buff = new BufferedReader (isr);

        String line;
        StringBuilder line2 = new StringBuilder();
        while((line = buff.readLine()) != null){
            line2.append(line + "\n");
        }

        boolean status = false;

        if(line2.toString().contains("RUNNING")){
            status = true;
        } else if(line2.toString().contains("STOPPED")){
            status = false;
        } else {
            status = false;
        }

        return status;
    }
}
