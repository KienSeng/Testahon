package JenkinsDeployment;

import Common.ApiConnector;

import java.util.HashMap;

/**
 * Created by kienseng on 4/18/2016.
 */
public class FromJenkinsApi {
    ApiConnector api;
    public void getLatestBuildInfo() throws Exception{
        HashMap<String, String> map = new HashMap<>();
        map.put("BuildName", api.getValueFromResponse("number"));
        map.put("FullDisplayName", api.getValueFromResponse("fullDisplayName"));
        map.put("Result", api.getValueFromResponse("result"));
        map.put("URL", api.getValueFromResponse("url"));
//        api.getValueFromResponse()
    }

    public void getResponseFromUrl(String url, String action) throws Exception{
        api = new ApiConnector();
        api.setPath(url);
        api.perform(action);
    }
}
