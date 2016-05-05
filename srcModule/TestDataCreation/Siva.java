package TestDataCreation;

import Common.ApiConnector;
import Common.DbConnector;
import Debugger.Logger;
import com.jayway.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.sql.ResultSet;

/**
 * Created by kienseng on 5/1/2016.
 */
public class Siva {
    static SeleniumAction action = new SeleniumAction();
    static ApiConnector api;
    static DbConnector db;
    static String environment;
    static FileInputStream file;
    String username = "";
    String apiKey = "";

    public void login(String username, String password) throws Exception{
        action.navigateToUrl("https://siva-" + environment.toLowerCase() + ".jobstreet.com/login.asp");
        action.enterTextToTextBox(By.name("LoginID"), username);
        action.enterTextToTextBox(By.name("Password"), password);
        action.pressButton(By.cssSelector("img"));
    }

    public void createSavedJob(String jobTitle) throws Exception{
        setupDbConnection();

        ResultSet rs = db.executeStatement("SELECT profile_id FROM advertiser_profile WITH (NOLOCK) WHERE advertiser_id = (SELECT advertiser_id FROM svuser WITH (NOLOCK) WHERE login_id = '" + username + "') AND profile_name IS NULL");
        int profileId = 0;
        while(rs.next()){
            profileId = rs.getInt("profile_id");
        }

        file = new FileInputStream("PostJob.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("\"replace_ProfileId\"", String.valueOf(profileId));
        fileContent = fileContent.replace("replace_JobTitle", jobTitle);

        String accessToken = getAccessToken();

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/jobs/me");
        api.setParameter("header", "Access-Token", accessToken);
        api.setApiKey(getApiKey());
        api.setPayload(fileContent);

        Response response = api.perform("POST");

        Logger.write("New job has been successfully created");
        Logger.write("Job ID: " + api.getValueFromResponse(response, "job_id"));
    }

    public void postSavedJob() throws Exception{

    }

    private String getAccessToken() throws Exception{
        api = new ApiConnector();
        file = new FileInputStream("AccessToken.json");

        setupDbConnection();

        ResultSet rs = db.executeStatement("SELECT hash_password FROM svuser WITH (NOLOCK) WHERE login_id='" + username + "'");
        String hashPassword = "";

        while(rs.next()){
            hashPassword = rs.getString("hash_password");
        }

        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("replace_UserName", username);
        fileContent = fileContent.replace("replace_HashPassword", hashPassword);

        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/auth/access-token");
        api.setApiKey(getApiKey());
        api.setPayload(fileContent);
        Response response = api.perform("POST");
        String accessToken = api.getValueFromResponse(response, "access_token");

        Logger.write("ACCESS TOKEN: " + accessToken);

        return accessToken;
    }

    public void setEnvironment(String env) throws Exception{
        environment = env;
    }

    private void setupDbConnection() throws Exception{
        db = new DbConnector();
        switch(environment.toLowerCase()){
            case "dev":
                db.connectDb("dbuser", "pew253", "penguin.jobstreet.com", 1433, "siva");
                break;

            case "qa":
                db.connectDb("dbuser", "pew253", "filly.jobstreet.com", 1433, "siva");
                break;

            case "ta":
                db.connectDb("dbuser", "pew253", "vela.jobstreet.com", 1433, "siva");
                break;

            case "stage":
                db.connectDb("dbuser", "pew253", "penguin.jobstreet.com", 1433, "siva");
                break;
        }
    }

    public void setUsername(String username) throws Exception{
        this.username = username;
    }

    private String getApiKey() throws Exception{
        String apiKey = "";

        switch(environment.toLowerCase()){
            case "dev":
                apiKey = "sivalocal";
                break;

            case "qa":
                apiKey = "sivalocal";
                break;

            case "ta":
                apiKey = "sivalocalta";
                break;

            case "stage":
                apiKey = "sivalocal";
                break;

            default:
                break;
        }
        Logger.write("API KEY: "+apiKey);
        return apiKey;
    }
}
