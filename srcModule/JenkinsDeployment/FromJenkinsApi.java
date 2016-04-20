package JenkinsDeployment;

import Common.ApiConnector;
import com.jayway.restassured.response.Response;

import java.util.HashMap;

/**
 * Created by kienseng on 4/18/2016.
 */
public class FromJenkinsApi {
    ApiConnector api;
    Response response;

    public HashMap<String, String> getLatestBuildInfo() throws Exception{
        HashMap<String, String> map = new HashMap<>();

        map.put("BuildName", api.getValueFromResponse(response, "number"));
        map.put("FullDisplayName", api.getValueFromResponse(response, "fullDisplayName"));
        map.put("Result", api.getValueFromResponse(response, "result"));
        map.put("URL", api.getValueFromResponse(response, "url"));

        return map;
    }

    public HashMap<String, String> getLastBuildNumber(int numberOfLastBuild) throws Exception{
        HashMap<String, String> map = new HashMap<>();

        for(int i = 0; i < numberOfLastBuild; i++){
            map.put("Build" + i, api.getValueFromResponse(response, "builds[" + i + "].number"));
        }

        return map;
    }

    public String getNextBuildNumber() throws Exception{
        return api.getValueFromResponse(response, "nextBuildNumber");
    }

    public void getResponseFromJenkins(String url, String action) throws Exception{
        api = new ApiConnector();
        api.setPath(url);
        response = api.perform(action);
    }

    public void setResponse(Response res) throws Exception{
        response = res;
    }
}
