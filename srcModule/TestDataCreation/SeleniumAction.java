package TestDataCreation;

import Debugger.Logger;
import Global.Global;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import javafx.application.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.logging.Level;

/*
 * Created by kienseng on 5/1/2016.
 */
public class SeleniumAction {
    Logger log;

    public void setupDriver() throws Exception{
        Global.driver = new HtmlUnitDriver(true);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        log.write("HTML Unit Global.driver created.");
    }

    public void navigateToUrl(String url) throws Exception{
        log.write("Navigating to: " + url);
        Global.driver.navigate().to(url);
        log.write("Currently at page: " + getCurrentUrl());
    }

    public void pressButton(By node) throws Exception{
        log.write("Locating: " + node.toString());
        Global.driver.findElement(node).click();
        log.write("Pressed Button: " + node.toString());
    }

    public void enterTextToTextBox(By node, String text) throws Exception{
        log.write("Locating: " + node.toString());
        System.out.println("                       " + Global.driver.getCurrentUrl());
        Global.driver.findElement(node).clear();
        Global.driver.findElement(node).sendKeys(text);
        log.write("Entered text: " + text);
        log.write("To textbox: " + node.toString());
    }

    public void SelectFromDropDown(By node, String selectByText) throws Exception{
        Select select = new Select(Global.driver.findElement(node));
        select.selectByVisibleText(selectByText);
    }

    public void selectFromCheckbox(By node) throws Exception{
        Global.driver.findElement(node).click();
    }

    public void selectFromRadioButton(By node) throws Exception{
        Global.driver.findElement(node).click();
    }

    public String getTextFromPage(By node) throws Exception{
        return Global.driver.findElement(node).getText();
    }

    public String getCurrentUrl() throws Exception{
        return Global.driver.getCurrentUrl();
    }

    public boolean checkElementExist(By node) throws Exception{
        try{
            return Global.driver.findElement(node).isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public String getValueFromAttribute(By node, String attribute) throws Exception{
        WebElement element = Global.driver.findElement(node);
        return element.getAttribute(attribute);
    }

    public void close() throws Exception{
        Global.driver.close();
    }
}
