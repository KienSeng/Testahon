package ServerMonitor;

import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kienseng.koh on 4/01/2016.
 */
public class ServiceCheck {

    public String checkWindowService(String serverAddress, String serviceName) throws Exception{

        System.out.println("sc \\\\" + serverAddress + " query \"" + serviceName + "\"");
        InputStream is = Runtime.getRuntime().exec("sc \\\\" + serverAddress + " query \"" + serviceName + "\"").getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buff = new BufferedReader (isr);

        String line;
        StringBuilder line2 = new StringBuilder();
        while((line = buff.readLine()) != null){
            line2.append(line + "\n");
        }

        String status = null;

        if(line2.toString().contains("RUNNING")){
            status = "RUNNING";
        } else if(line2.toString().contains("STOPPED")){
            status = "STOPPED";
        } else {
            status = "STOPPED";
        }

        return status;
    }

    public String checkSolrServices(String solrServerName, String coreName) throws Exception{
//        http://hydrus.jobstreet.com:8080/solr/applications/admin/ping
        URL url = new URL("http://" + solrServerName + ".jobstreet.com:8080/solr/" + coreName + "/admin/ping");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("Authorization", "Basic anNhcGk6cTZ0UmFkYXQ=");
        connection.setRequestMethod("GET");

        int code = 0;

        try{
            connection.connect();
            code = connection.getResponseCode();
        }catch(ConnectException e){
            return "STOPPED";
        }

        if(code == 200){
            return "RUNNING";
        } else{
            return "STOPPED";
        }
    }
}
