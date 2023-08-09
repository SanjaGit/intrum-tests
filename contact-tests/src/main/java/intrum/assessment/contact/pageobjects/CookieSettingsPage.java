package intrum.assessment.contact.pageobjects;

import intrum.assessment.framework.pageobjects.PageObject;
import intrum.assessment.framework.services.SeleniumService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static java.util.Arrays.asList;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class CookieSettingsPage extends PageObject {
    
    @Autowired
    private SeleniumService selenium;

    @Autowired
    public CookieSettingsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='onetrust-pc-sdk']")
    private WebElement modal;

    @FindBy(xpath = "//div[@class='ot-pc-scrollbar']/h3[1]")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[contains(@class,'onetrust-close-btn-handler')]")
    private WebElement btnConfirm;

    @Override
    public boolean isOpened() {
        selenium.waitForPageLoaded();
        List<WebElement> shouldAppear = new ArrayList<>(asList(
                pageTitle,
                btnConfirm
        ));
        shouldAppear.forEach(e -> selenium.waitElementAfterJSCompleted(e));
        selenium.areDisplayed(shouldAppear.toArray(new WebElement[0]));
        return pageTitle.isDisplayed();
    }

    @Override
    public boolean isVisible() {
        return modal.isDisplayed();
    }

    public void clickConfirmButton() {
        selenium.clickElementWithJavaScript(btnConfirm);
    }

}
