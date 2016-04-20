package Common;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by kienseng on 4/18/2016.
 */
public class ApiConnector {
    private RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
    private String path;
    private Response response;

    public void setPath(String url) throws Exception{
        path = url + "/api/json";
    }

    public void perform(String action) throws Exception{
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
    }

    public String getValueFromResponse(String jsonPath) throws Exception{
        return response.then().extract().path(path).toString();
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
}
