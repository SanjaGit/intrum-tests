package intrum.assessment.framework.config;

import intrum.assessment.framework.constants.Settings;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.springframework.core.env.Environment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

import static intrum.assessment.framework.constants.Settings.UI_DELAYS;

public class WebListener implements WebDriverListener {

    private final Environment environment;

    public WebListener(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
        if ("true".equalsIgnoreCase(environment.getProperty(UI_DELAYS))) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Settings.TIMEOUT_IMLICIT));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Settings.TIMEOUT_PAGE_LOAD));
        }
    }

    @Override
    public void afterGet(WebDriver driver, String url) { System.out.println("Get has been accepted"); }

    @Override
    public void afterAccept(Alert alert) {
        System.out.println("Alert has been accepted");
    }

    @Override
    public void afterDismiss(Alert alert) {
        System.out.println("Alert has been dismissed");
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, java.lang.String url) {
        System.out.println("Navigated to " + url);
    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        System.out.println("Page has been refreshed");
    }

    @Override
    public void beforeFindElement(WebElement element, By locator) {
        System.out.println("Trying to find element " + locator.toString());
    }

    @Override
    public void beforeClick(WebElement element) {
        System.out.println("Trying to click on " + element.toString());
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println("Element has been clicked " + element.toString());
    }

    @Override
    public void beforeSendKeys(WebElement element, java.lang.CharSequence... keysToSend) {
        System.out.println("Trying to change field: " + element.toString() +
                " with values: " + Arrays.toString(keysToSend));
    }

    @Override
    public void afterSendKeys(WebElement element, java.lang.CharSequence... keysToSend) {
        System.out.println("Value has been changed field: " + element.toString() +
                " with values: " + Arrays.toString(keysToSend));
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        if (e != null && e.getMessage() != null) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        System.out.println("Text of " + element.toString() + " is " + result);
    }
}
