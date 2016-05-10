package TestDataCreation;

import Common.ApiConnector;
import Common.DbConnector;
import Debugger.Logger;
import com.jayway.restassured.response.Response;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    public void login(String username, String password) throws Exception{
        action.setupDriver();
        action.navigateToUrl("https://siva-" + environment.toLowerCase() + ".jobstreet.com/login.asp");
        action.enterTextToTextBox(By.name("LoginID"), username);
        action.enterTextToTextBox(By.name("Password"), password);
        action.pressButton(By.xpath("//img[@src='/_pics/sign-in.gif']"));
    }

    public String createNormalJob(String jobTitle) throws Exception{
        setupDbConnection();

        ResultSet rs = db.executeStatement("SELECT profile_id FROM advertiser_profile WITH (NOLOCK) WHERE advertiser_id = (SELECT advertiser_id FROM svuser WITH (NOLOCK) WHERE login_id = '" + username + "') AND profile_name IS NULL");
        int profileId = 0;
        while(rs.next()){
            profileId = rs.getInt("profile_id");
        }

        file = new FileInputStream("Json/PostJob.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("\"replace_ProfileId\"", String.valueOf(profileId));
        fileContent = fileContent.replace("replace_JobTitle", jobTitle);
        System.out.println(fileContent);
        String accessToken = getAccessToken();

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/jobs/me");
        api.setParameter("header", "Access-Token", accessToken);
        api.setApiKey(getApiKey());
        api.setPayload(fileContent);

        Response response = api.perform("POST");
        response.prettyPrint();
        String job_id = api.getValueFromResponse(response, "job_id");

        Logger.write("New job has been successfully created");
        Logger.write("Job ID: " + job_id);

        return job_id;
    }

    public String createInternshipJob(String jobTitle) throws Exception{
        setupDbConnection();

        ResultSet rs = db.executeStatement("SELECT profile_id FROM advertiser_profile WITH (NOLOCK) WHERE advertiser_id = (SELECT advertiser_id FROM svuser WITH (NOLOCK) WHERE login_id = '" + username + "') AND profile_name IS NULL");
        int profileId = 0;
        while(rs.next()){
            profileId = rs.getInt("profile_id");
        }

        file = new FileInputStream("Json/PostInternshipJob.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("\"replace_ProfileId\"", String.valueOf(profileId));
        fileContent = fileContent.replace("replace_JobTitle", jobTitle);
        System.out.println(fileContent);
        String accessToken = getAccessToken();

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/jobs/me");
        api.setParameter("header", "Access-Token", accessToken);
        api.setApiKey(getApiKey());
        api.setPayload(fileContent);

        Response response = api.perform("POST");
        response.prettyPrint();
        String job_id = api.getValueFromResponse(response, "job_id");

        Logger.write("New job has been successfully created");
        Logger.write("Job ID: " + job_id);

        return job_id;
    }

    public String createJobWithSol(String jobTitle) throws Exception{
        setupDbConnection();

        ResultSet rs = db.executeStatement("SELECT profile_id FROM advertiser_profile WITH (NOLOCK) WHERE advertiser_id = (SELECT advertiser_id FROM svuser WITH (NOLOCK) WHERE login_id = '" + username + "') AND profile_name IS NULL");
        int profileId = 0;
        while(rs.next()){
            profileId = rs.getInt("profile_id");
        }

        file = new FileInputStream("Json/PostInternshipJob.json");
        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("\"replace_ProfileId\"", String.valueOf(profileId));
        fileContent = fileContent.replace("replace_JobTitle", jobTitle);
        System.out.println(fileContent);
        String accessToken = getAccessToken();

        api = new ApiConnector();
        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/jobs/me");
        api.setParameter("header", "Access-Token", accessToken);
        api.setApiKey(getApiKey());
        api.setPayload(fileContent);

        Response response = api.perform("POST");
        response.prettyPrint();
        String job_id = api.getValueFromResponse(response, "job_id");

        Logger.write("New job has been successfully created");
        Logger.write("Job ID: " + job_id);

        return job_id;
    }

    private String getAccessToken() throws Exception{
        api = new ApiConnector();
        file = new FileInputStream("Json/AccessToken.json");

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

        Logger.write("Access Token: " + accessToken);

        return accessToken;
    }

    public void setEnvironment(String env) throws Exception{
        environment = env;
    }

    private void setupDbConnection() throws Exception{
        db = new DbConnector();
        switch(environment.toLowerCase()){
            case "dev":
                db.connectDb("dbuser", "pew253", "cettia.jobstreet.com", 1433, "siva");
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

        return apiKey;
    }

    public void postJob(String order, String jobId) throws Exception{
        int contractItemId = 0;
        int orderNumber = Integer.parseInt(order.substring(2, order.length()));
        String orderPrefix = order.substring(0,2);
        String accessToken = getAccessToken();

        System.out.println(orderPrefix + "\t\t" + orderNumber);

        setupDbConnection();
        ResultSet rs = db.executeStatement("SELECT contract_item_id from contract_item WHERE contract_id = (SELECT contract_id from contract where order_number = " + orderNumber + " and order_prefix = '" + orderPrefix + "')");
//        SELECT contract_item_id from contract_item WHERE contract_id = (SELECT contract_id from contract where order_number = 74 and order_prefix = 'MY')

        while(rs.next()){
            contractItemId = rs.getInt("contract_item_id");
            System.out.println(contractItemId);
        }

        api = new ApiConnector();
        file = new FileInputStream("Json/CreditConsumption.json");

        String fileContent = IOUtils.toString(file, "UTF-8");
        fileContent = fileContent.replace("\"replace_salesOrderItemId\"", String.valueOf(contractItemId));
        fileContent = fileContent.replace("\"replace_productServiceCode\"", "1");

        api.setPath("http://api-" + environment.toLowerCase() + ".jobstreet.com:80/v/jobs/me/{job_id}/post");
        api.setApiKey(getApiKey());
        api.setParameter("path", "job_id", jobId);
        api.setParameter("header", "Access-Token", accessToken);
        api.setPayload(fileContent);
        Response response = api.perform("PUT");
        response.prettyPrint();
        String jobUrl = api.getValueFromResponse(response, "url");
        Logger.write(jobUrl);
    }

    public ArrayList<String> getAdvertiserOrderList(String userName) throws Exception{
        String orderName;
        ArrayList<String> packageList = new ArrayList<>();

        setupDbConnection();
        ResultSet rs;
        rs = db.executeStatement("SELECT order_prefix, order_number, product_service_name FROM contract_item INNER JOIN contract ON contract_item.contract_id = contract.contract_id INNER JOIN svuser ON contract.advertiser_id = svuser.advertiser_id INNER JOIN ref_product_service ON ref_product_service.product_service_code = contract_item.product_service_code WHERE svuser.login_id = '" + userName + "'");

        while(rs.next()){
            orderName = rs.getString("order_prefix") + rs.getString("order_number") + " - " + rs.getString("product_service_name");
            packageList.add(orderName);
        }

        return packageList;
    }
}
