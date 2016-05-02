package TestDataCreation;

import Debugger.Logger;
import Global.Global;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import javafx.application.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.logging.Level;

/**
 * Created by kienseng on 5/1/2016.
 */
public class SeleniumAction {
    static WebDriver driver;
    Logger log;

    public void setupDriver() throws Exception{
        WebDriver driver = new HtmlUnitDriver(true);
        this.driver = driver;
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        log.write("HTML Unit driver created.");
    }

    public void navigateToUrl(String url) throws Exception{
        log.write("Navigating to: " + url);
        driver.navigate().to(url);
        log.write("Currently at page: " + getCurrentUrl());
    }

    public void pressButton(By node) throws Exception{
        log.write("Locating: " + node.toString());
        driver.findElement(node).click();
        log.write("Pressed Button: " + node.toString());
    }

    public void enterTextToTextBox(By node, String text) throws Exception{
        log.write("Locating: " + node.toString());
        driver.findElement(node).clear();
        driver.findElement(node).sendKeys(text);
        log.write("Entered text: " + text);
        log.write("To textbox: " + node.toString());
    }

    public void SelectFromDropDown(By node, String selectByText) throws Exception{
        Select select = new Select(driver.findElement(node));
        select.selectByVisibleText(selectByText);
    }

    public void selectFromCheckbox(By node) throws Exception{
        driver.findElement(node).click();
    }

    public void selectFromRadioButton(By node) throws Exception{
        driver.findElement(node).click();
    }

    public String getTextFromPage(By node) throws Exception{
        return driver.findElement(node).getText();
    }

    public String getCurrentUrl() throws Exception{
        return driver.getCurrentUrl();
    }

    public void close() throws Exception{
        driver.close();
    }

    private void displayInConsole() throws Exception{
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }
}
