package TestDataCreation;

import Common.DbConnector;
import Debugger.Logger;
import Global.Global;
import javafx.application.Platform;
import org.openqa.selenium.By;
import userInterface.TestDataCreationController;

/**
 * Created by kienseng on 5/1/2016.
 */
public class Myjs {
    static SeleniumAction action = new SeleniumAction();
    static String environment;

    public void updateCandidateVerificationStatus(String email, int status) throws Exception{
        DbConnector db = new DbConnector();
        String dbName = "";

        Logger.write("Update candidate's email validation status.");

        switch(environment.toLowerCase()){
            case "dev":
                dbName = "myjsdb_int2";
                Logger.write("Connecting to bat.jobstreet.com.");
                db.connectMysqlDb("default", "mysqladmin84MIMA", "bat", 3309, dbName);
                break;

            case "qa":
                dbName = "myjsdb_int2";
                Logger.write("Connecting to drone.jobstreet.com.");
                db.connectMysqlDb("default", "mysqladmin84MIMA", "drone.jobstreet.com", 3306, dbName);
                break;

            case "ta":
                dbName = "myjsdb";
                Logger.write("Connecting to cormorant.jobstreet.com.");
                db.connectMysqlDb("default", "mysqladmin84MIMA", "cormorant", 3306, dbName);
                break;

            case "stage":
                dbName = "myjsdb";
                Logger.write("Connecting to .");
                break;

            default:
                break;
        }

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

    public void updateProfile(String module) throws Exception{
        action.pressButton(By.id("header_myjobstreet_link"));
        action.pressButton(By.id("header_work_exp_link"));
    }

    public void updateResumes() throws Exception{
        action.navigateToUrl("http://myjobstreet-" + environment + ".jobstreet.com.my/resume/edit-work-experience.php");
    }

    public void closeFirstTimeLoginPrompt() throws Exception{
        Logger.write("\t\t" + action.getCurrentUrl());
        if(action.checkElementExist(By.id("lbl_main_header"))){
            Logger.write("\t\t" + action.getValueFromAttribute(By.xpath("//a[@class='skip-close']"), "href"));
            action.navigateToUrl(action.getValueFromAttribute(By.xpath("//a[@class='skip-close']"), "href"));
        }
    }

    public void firstTimeLoginFillIn(String email, String password, String positionTitle, String companyName)throws Exception{
        action.close();
        login(email, password);
        closeFirstTimeLoginPrompt();

        action.pressButton(By.id("header_myjobstreet_link"));
        action.pressButton(By.id("header_create_resume"));

        //Education
        Thread.sleep(4000);
        action.enterTextToTextBox(By.id("institute_name"),"HELP College University");
        action.pressButton(By.id("s2id_qualification_code"));
        action.pressButton(By.id("select2-result-label-258"));
        action.pressButton(By.id("s2id_field_of_study_code"));
        action.pressButton(By.id("select2-result-label-276"));
        action.enterTextToTextBox(By.id("graduation_year"), "2020");
        action.pressButton(By.id("save"));

        //Experience
        Thread.sleep(4000);
        action.pressButton(By.id("btn_experience_label"));
        action.pressButton(By.id("select2-chosen-346"));
        action.pressButton(By.id("select2-result-label-430"));
        action.enterTextToTextBox(By.id("position_title"), positionTitle);
        action.enterTextToTextBox(By.id("company_name"), companyName);
        action.pressButton(By.id("s2id_specialization_code"));
        action.pressButton(By.id("select2-result-label-699"));
        action.pressButton(By.id("s2id_primary_role"));
        action.pressButton(By.id("select2-result-label-767"));
        action.pressButton(By.id("s2id_industry_code"));
        action.pressButton(By.id("select2-result-label-774"));
        action.pressButton(By.id("s2id_position_level_code"));
        action.pressButton(By.id("select2-result-label-832"));
        action.pressButton(By.id("s2id_join_date_month"));
        action.pressButton(By.id("select2-result-label-851"));
        action.enterTextToTextBox(By.id("join_date_year"), "2010");
        action.pressButton(By.id("select2-chosen-353"));
        action.pressButton(By.id("select2-result-label-862"));
        action.enterTextToTextBox(By.id("left_date_year"), "2015");
        action.pressButton(By.id("save"));

        //About Me
        Thread.sleep(4000);
        action.pressButton(By.id("s2id_nationality_code"));
        action.pressButton(By.id("select2-result-label-879"));
        action.pressButton(By.id("s2id_state_code"));
        action.pressButton(By.id("select2-result-label-1378"));
        action.pressButton(By.id("s2id_handphone_country_code"));
        action.pressButton(By.id("select2-result-label-1402"));
        action.enterTextToTextBox(By.id("handphone_no"), "161234567");
        action.pressButton(By.id("save_btn"));
        action.pressButton(By.id("complete"));
        Logger.write("Resume successfully created.");
    }

    public void logout() throws Exception{
        action.pressButton(By.id("header_login_menu"));
        action.pressButton(By.id("header_logout_link"));
    }

    public void setEnvironment(String env) throws Exception{
        environment = env;
    }
}
