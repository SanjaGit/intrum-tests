package intrum.assessment.framework.services;

import intrum.assessment.framework.constants.Settings;
import intrum.assessment.framework.pageobjects.PageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.junit.jupiter.api.Assertions.*;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class SeleniumService extends PageObject {

    private final JavascriptExecutor js = (JavascriptExecutor) driver;
    private final Actions actions = new Actions(driver);

    @Autowired
    public SeleniumService(WebDriver driver) {
        super(driver);
    }

    public void areDisplayed(WebElement[] elements, int timeOut) {
        List<WebElement> elementList = Arrays.asList(elements);
        this.wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
    }

    public void areDisplayed(WebElement[] elements) {
        areDisplayed(elements, Settings.TIMEOUT_VISIBILITY);
    }

    public List<String> getDropDownValues(WebElement dropDownList) {
        List <String> listDropDownValues = new ArrayList<>();

        Select select = new Select(dropDownList);
        List<WebElement> dropDownValues = select.getOptions();

        log.writeProgress("Actual values:");
        for (WebElement item : dropDownValues) {
            listDropDownValues.add(item.getText());
        }
        return listDropDownValues;

    }

    public void clickElementWithJavaScript(WebElement element) {
        waitForJStoLoad();
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        js.executeScript("arguments[0].scrollIntoView();", element);
        js.executeScript("arguments[0].click();", element);
        waitForJStoLoad();
        waitForInvisibilityOfOverlayNewComp();
    }

    public void waitElementAfterJSCompleted(WebElement element) {
        waitForJStoLoad();
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView();", element);
        waitForJStoLoad();
    }

    public boolean waitForJStoLoad() {

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) js.executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                return true;
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> js.executeScript("return document.readyState").toString().equals("complete");

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public void waitForPageLoaded() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading01")));
        }
        catch(Exception e) {
            System.out.println("Page is loaded");
        }
    }

    public void waitForInvisibilityOfOverlayNewComp() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".-loading-inner")));
        }
        catch(Exception e) {
            System.out.println("No overlay");
        }
    }

    public void enterValue(WebElement element, String value) {
        waitForJStoLoad();
        element.clear();
        element.sendKeys(value);
    }

}
