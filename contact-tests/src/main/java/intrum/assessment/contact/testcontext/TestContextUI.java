package intrum.assessment.contact.testcontext;

import intrum.assessment.contact.config.SpringContextConfig;
import intrum.assessment.framework.config.SeleniumConfig;
import intrum.assessment.framework.config.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import io.qameta.allure.Allure;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringContextConfig.class)
@CucumberContextConfiguration
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class TestContextUI extends WebDriverFactory {

    @Autowired
    private SeleniumConfig config;

    @Before
    public void beforeStartUp(Scenario scenario) {
        super.startUp(scenario.getName());
    }

    @After
    public void afterTearDown(Scenario scenario) {
        super.tearDown(scenario.getName(), scenario.isFailed());
    }

    @Override
    public void performOnFailure(String scenarioName) {
        Allure.addAttachment(scenarioName,
                new ByteArrayInputStream(((TakesScreenshot) config.getDriver())
                        .getScreenshotAs(OutputType.BYTES)));
    }

    @Override
    public void performOnQuit() {
        this.config.quitDriver();
    }

}
