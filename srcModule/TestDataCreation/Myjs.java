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
public class Myjs {
    static SeleniumAction action = new SeleniumAction();
    static ApiConnector api;
    static DbConnector db;
    static String environment;
    static FileInputStream file;

    private void connectToDb() throws Exception{
        db = new DbConnector();

        Logger.write("Connecting to DB.");

        switch(environment.toLowerCase()){
            case "dev":
                Logger.write("Connecting to bat.jobstreet.com.");
                db.connectMysqlDb("default", "mysqladmin84MIMA", "bat", 3309, "myjsdb_int2");
                break;

            case "qa":
                Logger.write("Connecting to drone.jobstreet.com.");
                db.connectMysqlDb("default", "mysqladmin84MIMA", "drone.jobstreet.com", 3306, "myjsdb_int_test2");
                break;

            case "ta":
                Logger.write("Connecting to cormorant.jobstreet.com.");
                db.connectMysqlDb("default", "mysqladmin84MIMA", "cormorant", 3306, "myjsdb");
                break;

            case "stage":
                Logger.write("Connecting to .");
                break;

            default:
                break;
        }
    }

    public String getCandidateAccessToken(String email) throws Exception{
        Logger.write("Retrieving candidate's encrypted password.");
        connectToDb();

        String hashPassword = "";
        ResultSet rs = db.executeStatement("SELECT password FROM candidate where login_id = '" + email + "'");

        while(rs.next()){
            hashPassword = rs.getString("password");
        }

        api = new ApiConnector();
        file = new FileInputStream("Json/AccessToken.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("replace_UserName", email);
        fileContent = fileContent.replace("replace_HashPassword", hashPassword);

        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/auth/access-token");
        api.setApiKey(getApiKey());
        api.setPayload(fileContent);
        Response response = api.perform("POST");
        String accessToken = api.getValueFromResponse(response, "access_token");
        response.prettyPrint();
        Logger.write("ACCESS TOKEN: " + accessToken);

        return accessToken;
    }

    private String getApiKey() throws Exception{
        String apiKey = "";

        switch(environment.toLowerCase()){
            case "dev":
                apiKey = "myjslocalmy";
                break;

            case "qa":
                apiKey = "myjslocalmy";
                break;

            case "ta":
                apiKey = "myjslocaltasg";
                break;

            case "stage":
                apiKey = "myjslocalsg";
                break;

            default:
                break;
        }

        Logger.write("API KEY: "+apiKey);

        return apiKey;
    }

    public void updateCandidateVerificationStatus(String email, int status) throws Exception{
        Logger.write("Update candidate's email validation status.");

        connectToDb();

        Logger.write("Updating candidate email_status_code.");

        db.executeUpdate("UPDATE candidate SET email_status_code = " + status + " WHERE email = '" + email + "'");
        db.closeStatement();

        Logger.write("Update successful");
    }

    public void login(String email, String password) throws Exception{
        action.setupDriver();
        action.navigateToUrl("https://myjobstreet-" + environment + ".jobstreet.com/home/login.php?site=");
        action.enterTextToTextBox(By.id("login_id"), email);
        action.enterTextToTextBox(By.id("password"), password);
        action.pressButton(By.id("btn_login"));
    }

    public void candidateSignUp(String firstName, String lastName, String email, String password) throws Exception{
        action.setupDriver();

        switch(environment.toLowerCase()){
            case "dev":
                action.navigateToUrl("https://myjobstreet-dev.jobstreet.com.my/registration/simple-signup.php");
                break;

            case "qa":
                action.navigateToUrl("https://myjobstreet-qa.jobstreet.com.my/registration/simple-signup.php");
                break;

            case "ta":
                action.navigateToUrl("https://myjobstreet-ta.jobstreet.com.my/registration/simple-signup.php");
                break;

            case "stage":
                action.navigateToUrl("https://myjobstreet-stage.jobstreet.com.my/registration/simple-signup.php");
                break;

            default:
                break;
        }

        action.enterTextToTextBox(By.id("first_name"), firstName);
        action.enterTextToTextBox(By.id("last_name"), lastName);
        action.enterTextToTextBox(By.id("email"), email);
        action.enterTextToTextBox(By.id("password"), password);
        action.pressButton(By.id("signup_btn"));

        if(action.getCurrentUrl().contains("/resume/create-profile-about-me.php")){
            Logger.write("Candidate successfully created.");
            Logger.write("Email: " + email);
            Logger.write("Password: " + password);
            Logger.write("First Name: " + firstName);
            Logger.write("Last Name: " + lastName);
        }else{
            Logger.write("Failed to create candidate. Please check error!");
        }
    }

    public void updateExperience(String accessToken, String companyName, String positionTitle) throws Exception{
        Logger.write("Updating candidate's experience.");
        Logger.write("\t\t" + accessToken);

        file = new FileInputStream("Json/PostExperience.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("replace_companyName", companyName);
        fileContent = fileContent.replace("replace_positionTitle", positionTitle);

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/resumes/me/experiences");
        api.setApiKey(getApiKey());
        api.setParameter("header", "Access-Token", accessToken);
        api.setPayload(fileContent);
        Response response = api.perform("POST");

        response.prettyPrint();
        Logger.write("Candidate's experience has been updated.");
    }

    public void updateEducation(String accessToken) throws Exception{
        Logger.write("Updating candidate's education.");
        Logger.write("\t\t" + accessToken);

        file = new FileInputStream("Json/PostEducation.json");
        String fileContent = IOUtils.toString(file, "UTF-8");

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/resumes/me/educations");
        api.setApiKey(getApiKey());
        api.setParameter("header", "Access-Token", accessToken);
        api.setPayload(fileContent);
        Response response = api.perform("POST");

        response.prettyPrint();
        Logger.write("Candidate's education has been updated.");
    }

    public void updatePersonalInfo(String accessToken, String firstName, String lastName) throws Exception{
        Logger.write("Updating candidate's personal info.");
        Logger.write("\t\t" + accessToken);

        file = new FileInputStream("Json/PutPersonal.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("replace_firstName", firstName);
        fileContent = fileContent.replace("replace_lastName", lastName);

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/candidates/me/personal");
        api.setApiKey(getApiKey());
        api.setParameter("header", "Access-Token", accessToken);
        api.setPayload(fileContent);
        Response response = api.perform("PUT");

        response.prettyPrint();
        Logger.write("Candidate's personal info has been updated.");
    }

    public void logout() throws Exception{
        action.pressButton(By.id("header_login_menu"));
        action.pressButton(By.id("header_logout_link"));
    }

    public void setEnvironment(String env) throws Exception{
        environment = env;
    }
}
