package TestDataCreation;

import Common.DbConnector;
import org.openqa.selenium.By;

/**
 * Created by kienseng on 5/1/2016.
 */
public class Myjs {
    static SeleniumAction action = new SeleniumAction();
    static String environment;

    public static void updateCandidateVerificationStatus(String environment, String email, int status) throws Exception{
        DbConnector db = new DbConnector();
        String dbName = "";

        switch(environment.toLowerCase()){
            case "dev":
                dbName = "myjsdb_int2";
                db.connectDb("default", "mysqladmin84MIMA", "bat", 3309, "myjsdb_int_test2");
                break;

            case "qa":
                dbName = "myjsdb_int2";
                db.connectDb("default", "mysqladmin84MIMA", "drone.jobstreet.com", 3306, "myjsdb_int_test2");
                break;

            case "ta":
                dbName = "myjsdb";
                db.connectDb("default", "mysqladmin84MIMA", "cormorant", 3306, "myjsdb");
                break;

            default:
                break;
        }

        db.executeStatement("UPDATE candidate SET email_status_code = " + status + " WHERE email = '" + email + "'");
        db.closeStatement();
    }

    public static void login(String email, String password) throws Exception{
        action.setupDriver();
        action.navigateToUrl("https://myjobstreet.jobstreet.com/home/login.php?site=");
        action.enterTextToTextBox(By.id("login_id"), email);
        action.enterTextToTextBox(By.id("password"), password);
        action.pressButton(By.id("btn_login"));
    }

    public static void candidateSignUp(String firstName, String lastName, String email, String password) throws Exception{
        action.navigateToUrl("https://myjobstreet-ta.jobstreet.com.my/registration/simple-signup.php");
        action.enterTextToTextBox(By.id("first_name"), firstName);
        action.enterTextToTextBox(By.id("last_name"), lastName);
        action.enterTextToTextBox(By.id("email"), email);
        action.enterTextToTextBox(By.id("password"), password);
        action.pressButton(By.id("signup_btn"));
    }

    public static void updateProfile(String module) throws Exception{
        action.pressButton(By.id("header_myjobstreet_link"));
        action.pressButton(By.id("header_work_exp_link"));
    }

    public static void updateResumes() throws Exception{

    }

    public static void closeFirstTimeLoginPrompt() throws Exception{
        if(action.checkElementExist(By.id("profile_about_me"))){
            action.pressButton(By.xpath("//a[@class='skip-close']"));
        }
    }

    public static void logout() throws Exception{
        action.pressButton(By.id("header_login_menu"));
        action.pressButton(By.id("header_logout_link"));
    }
}
