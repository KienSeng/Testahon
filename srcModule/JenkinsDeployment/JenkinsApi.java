package JenkinsDeployment;

import Common.ApiConnector;
import com.jayway.restassured.response.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kienseng on 4/18/2016.
 */
public class JenkinsApi {
    ApiConnector api;
    Response response;

    public HashMap<String, String> getMainJobBuildInfo() throws Exception{
        HashMap<String, String> map = new HashMap<>();

        map.put("DisplayName", api.getValueFromResponse(response, "displayName"));
        map.put("Url", api.getValueFromResponse(response, "url"));
        map.put("LatestBuild", api.getValueFromResponse(response, "builds[0].number"));

        return map;
    }

    public HashMap<String, String> getBuildInfo() throws Exception{
        HashMap<String, String> map = new HashMap<>();

        map.put("BuildNumber", api.getValueFromResponse(response, "number"));
        map.put("FullDisplayName", api.getValueFromResponse(response, "fullDisplayName"));
        map.put("Result", api.getValueFromResponse(response, "result"));
        map.put("Url", api.getValueFromResponse(response, "url"));
        map.put("TriggerBy", api.getValueFromResponse(response, "culprits.fullName").replace("[","").replace("]",""));
        map.put("TriggerDateTime", api.getValueFromResponse(response, "date"));
        System.out.println(map.get("BuildName"));
        System.out.println(map.get("FullDisplayName"));
        System.out.println(map.get("Result"));
        System.out.println(map.get("URL"));
        System.out.println(map.get("TriggerBy"));
        return map;
    }

    public ArrayList<String> getLastBuildNumberExcludeLatest(int numberOfLastBuild) throws Exception{
        ArrayList<String> buildArray = new ArrayList<>();

        for(int i = 1; i <= numberOfLastBuild; i++){
            buildArray.add(api.getValueFromResponse(response, "builds[" + i + "].number") + "|" + api.getValueFromResponse(response, "builds[" + i + "].url"));
        }

        return buildArray;
    }

    public void getResponseFromJenkins(String url, String action) throws Exception{
        api = new ApiConnector();
        api.setPath(url);
        response = api.perform(action);
    }

    public void setResponse(Response res) throws Exception{
        response = res;
    }

    public Response getResponse() throws Exception{
        return response;
    }
}
