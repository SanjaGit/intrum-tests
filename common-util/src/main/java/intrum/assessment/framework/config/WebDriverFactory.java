package intrum.assessment.framework.config;

import intrum.assessment.framework.util.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static intrum.assessment.framework.constants.Settings.UI_DEBUG;
import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public abstract class WebDriverFactory implements IWebDriverFactory {

    public final Log log = new Log();

    protected static String downloadDir = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(File.separator))
            + File.separator + "trading-tests" + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "ui"
            + File.separator + "download" + File.separator;

    protected static ChromeDriver chromeDriver;
    protected static WebDriver driver;

    public static WebDriver factory(Environment environment, String browserName) {
        switch (browserName) {
            case "chrome": {
                System.out.println("Starting Chrome driver instance -----------------------------------");

                System.setProperty("webdriver.chrome.driver", environment.getRequiredProperty("driver"));

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized ");
                if (!("true".equalsIgnoreCase(environment.getProperty(UI_DEBUG)))) {
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                chromeOptions.addArguments("enable-automation");
                if (!("true".equalsIgnoreCase(environment.getProperty(UI_DEBUG)))) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--dns-prefetch-disable");
                chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setAcceptInsecureCerts(true);

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.default_directory", downloadDir);
                prefs.put("download.prompt_for_download", "false");
                prefs.put("download.directory_upgrade", "true");
                prefs.put("safebrowsing.enabled", "true");
                prefs.put("plugins.always_open_pdf_externally", true);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeDriver = new ChromeDriver(chromeOptions);
                WebListener webListener = new WebListener(environment);
                EventFiringDecorator<ChromeDriver> decorator = new EventFiringDecorator<>(webListener);
                driver = decorator.decorate(chromeDriver);
                System.out.println("Chrome is started successfully! Testing! Go go go");
                return driver;
            }

            case "edge": {
                return new EdgeDriver();
            }

            default:
                throw new IllegalArgumentException(browserName + " not recognized");
        }
    }

    protected void startUp(String scenarioName) {
        log.writeProgress("[STARTED] Scenario: " + scenarioName);
    }
    
    protected void tearDown(String scenarioName, boolean isFailed) {
        try {
            performOnCleanData();
            log.writeProgress("Cleaning data after test scenario execution done");
        } catch (Exception e) {
            log.writeProgress("Cleaning data after test scenario execution failed");
        }
        if (isFailed) {
            try {
                performOnFailure(scenarioName);
                log.writeProgress("Test failed and Screenshot is taken for failed scenario: " + scenarioName);
            } catch (Exception e) {
                log.writeProgress("Test failed and Screenshot is not taken for failed scenario: " + scenarioName);
            }
        }
        try {
            performOnQuit();
            log.writeProgress("Test execution completed and the web driver closed");
        } catch (Exception e) {
            log.writeProgress("Test execution completed and the web driver not closed");
        }
        log.writeProgress("[ENDED] Scenario: " + scenarioName);
    }

    @Override
    public void performOnCleanData() {}

    @Override
    public void performOnFailure(String scenarioName) {}

    @Override
    public void performOnQuit() {}
}
