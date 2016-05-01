package TestDataCreation;

import Common.DbConnector;
import org.openqa.selenium.By;

/**
 * Created by kienseng on 5/1/2016.
 */
public class Myjs {
    static SeleniumAction action = new SeleniumAction();

    public static void updateCandidateVerificationStatus(String environment, String email) throws Exception{
        DbConnector db = new DbConnector();
        String dbName = "";

        switch(environment.toLowerCase()){
            case "dev":
                dbName = "myjsdb_int2";
                db.connectDb("username", "password", "serverName", 3306, "myjsdb_int2");
                break;

            case "qa":
                dbName = "myjsdb_int2";
                db.connectDb("username", "password", "drone.jobstreet.com", 3306, "myjsdb_int2");
                break;

            case "ta":
                dbName = "myjsdb";
                db.connectDb("username", "password", "serverName", 3306, "myjsdb");
                break;

            default:
                break;
        }

        db.executeStatement("UPDATE " + dbName + "SET verification_status = 1 WHERE email = '" + email + "'");
        db.closeStatement();
    }

    public static void login(String email, String password) throws Exception{
        action.setupDriver();
        action.navigateToUrl("https://myjobstreet.jobstreet.com/home/login.php?site=");
        action.enterTextToTextBox(By.id("login_id"), email);
        action.enterTextToTextBox(By.id("password"), password);
        action.pressButton(By.id("btn_login"));
    }

    public static void updateProfile(String module) throws Exception{
        action.pressButton(By.id("header_myjobstreet_link"));
        action.pressButton(By.id("header_work_exp_link"));
    }

    public static void updateResumes() throws Exception{

    }

    public static void logout() throws Exception{
        action.pressButton(By.id("header_login_menu"));
        action.pressButton(By.id("header_logout_link"));
    }
}
