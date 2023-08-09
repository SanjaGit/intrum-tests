package intrum.assessment.contact.pageobjects;

import intrum.assessment.framework.pageobjects.PageObject;
import intrum.assessment.framework.services.SeleniumService;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class CommonPage extends PageObject {

    @Autowired
    private SeleniumService selenium;

    @Autowired
    public CommonPage(WebDriver driver) {
        super(driver);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
