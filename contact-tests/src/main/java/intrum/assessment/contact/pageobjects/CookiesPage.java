package intrum.assessment.contact.pageobjects;

import intrum.assessment.framework.pageobjects.PageObject;
import intrum.assessment.framework.services.SeleniumService;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static java.util.Arrays.asList;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class CookiesPage extends PageObject {

    @Autowired
    private SeleniumService selenium;

    @Autowired
    public CookiesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='onetrust-banner-sdk']")
    private WebElement modal;

    @FindBy(xpath = "//h3[@id='onetrust-policy-title']")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[@id='onetrust-accept-btn-handler']")
    private WebElement btnAccept;
    @FindBy(xpath = "//button[@id='onetrust-reject-all-handler']")
    private WebElement btnReject;
    @FindBy(xpath = "//button[@id='onetrust-pc-btn-handler']")
    private WebElement btnSettings;

    @Override
    public boolean isOpened() {
        selenium.waitForPageLoaded();
        List<WebElement> shouldAppear = new ArrayList<>(asList(
                pageTitle,
                btnAccept, btnReject, btnSettings
        ));
        shouldAppear.forEach(e -> selenium.waitElementAfterJSCompleted(e));
        selenium.areDisplayed(shouldAppear.toArray(new WebElement[0]));
        return pageTitle.isDisplayed();
    }

    @Override
    public boolean isVisible() {
        return modal.isDisplayed();
    }

    public void clickAcceptButton() {
        selenium.clickElementWithJavaScript(btnAccept);
    }

}
