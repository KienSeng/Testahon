package Common;

import Debugger.Logger;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.sql.ResultSet;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by kienseng on 4/18/2016.
 */
public class ApiConnector {
    private RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
    private String path;
    private Response response;

    public void setPath(String url) throws Exception{
        path = url;
        Logger.write("\t\t" + path);
    }

    public Response perform(String action) throws Exception{
        RequestSpecification requestCompiler = requestBuilder.build();

        switch(action.toUpperCase()){
            case "POST":
                response = given().spec(requestCompiler).when().post(path);
                break;
            case "GET":
                response = given().spec(requestCompiler).when().get(path);
                break;
            case "PUT":
                response = given().spec(requestCompiler).when().put(path);
                break;
            case "DELETE":
                response = given().spec(requestCompiler).when().delete(path);
                break;
        }
        return response;
    }

    public String getValueFromResponse(Response response, String jsonPath) throws Exception{
        String value = null;
        try{
            value = response.then().extract().path(jsonPath).toString();
        }catch(Exception e){
            e.printStackTrace();
            value = null;
        }
        return value;
    }

    public void setParameter(String paramType, String key, String value) throws Exception{
        switch(paramType.toLowerCase()){
            case "path":
                requestBuilder.addPathParam(key, value);
                break;

            case "query":
                requestBuilder.addQueryParam(key, value);
                break;

            case "header":
                requestBuilder.addHeader(key, value);
                break;
        }
    }

    public String getResponse() throws Exception{
        return response.prettyPrint();
    }

    public void setPayload(String payload) throws Exception{
        requestBuilder.setContentType("application/json; charset=UTF-8");
        requestBuilder.setBody(payload);
    }


    public void setApiKey(String key) throws Exception {
        setParameter("query", "api_key", key);
    }
}
