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

    public HashMap<String, String> getBuildInfo() throws Exception{
        HashMap<String, String> map = new HashMap<>();

        map.put("BuildName", api.getValueFromResponse(response, "number"));
        map.put("FullDisplayName", api.getValueFromResponse(response, "fullDisplayName"));
        map.put("Result", api.getValueFromResponse(response, "result"));
        map.put("URL", api.getValueFromResponse(response, "url"));
        map.put("TriggerBy", api.getValueFromResponse(response, "culprits.fullName").replace("[","").replace("]",""));
        map.put("TriggerDateTime", api.getValueFromResponse(response, "date"));
        System.out.println(map.get("BuildName"));
        System.out.println(map.get("FullDisplayName"));
        System.out.println(map.get("Result"));
        System.out.println(map.get("URL"));
        System.out.println(map.get("TriggerBy"));
        return map;
    }

    public HashMap<String, String> getLastBuildNumber(int numberOfLastBuild) throws Exception{
        HashMap<String, String> map = new HashMap<>();

        for(int i = 0; i < numberOfLastBuild; i++){
            map.put("Build" + i, api.getValueFromResponse(response, "builds[" + i + "].number") + "|" + api.getValueFromResponse(response, "builds[" + i + "].url"));
        }

        return map;
    }

    public ArrayList<String> getDownstreamBuild() throws Exception{
        String downstreamName = api.getValueFromResponse(response, "downstreamProjects.name");
        String downstreamUrl = api.getValueFromResponse(response, "downstreamProjects.url");

        String[] splittedDownstreamName = downstreamName.split(",");
        String[] splittedDownstreamUrl = downstreamUrl.split(",");
        ArrayList<String> downstreamArray = new ArrayList<>();

        for (int i = 0; i < splittedDownstreamName.length; i++){
            downstreamArray.add(splittedDownstreamName[i] + "|" + splittedDownstreamUrl[i]);
            System.out.println(splittedDownstreamName[i] + "|" + splittedDownstreamUrl[i]);
        }

        return downstreamArray;
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

    public Response getResponse() throws Exception{
        return response;
    }
}
