package intrum.assessment.contact.pageobjects;

import intrum.assessment.contact.constants.ContactParams;
import intrum.assessment.framework.constants.Params;
import intrum.assessment.framework.pageobjects.PageObject;
import intrum.assessment.framework.services.SeleniumService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static java.util.Arrays.asList;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class ContactPage extends PageObject {

    @Autowired
    private SeleniumService selenium;

    @Value("classpath:pages/contact.yml")
    private Resource resource;

    @Autowired
    public ContactPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='header-container']//h1")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[@class='information-bar-container']")
    private WebElement infoBarContainer;

    @FindBy(xpath = "//div[@id='slide']")
    private WebElement contentRequestModal;

    @FindBy(xpath = "//div[@class='search-container']/span")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@class='search-input']")
    private WebElement searchInput;

    @FindBy(xpath = "//a[contains(@class,'login-button')]")
    private WebElement loginButton;

    @FindBy(xpath = "//button[@class='ot-floating-button__open']")
    private WebElement cookiesButton;

    @FindBy(xpath = "//div[@class='CTAButton']/a")
    private WebElement requestButton;

    private String searchCriteria = Params.EMPTY;

    public String getSearchCriteria() {
        return searchCriteria;
    }

    @Override
    public boolean isOpened() {
        selenium.waitForPageLoaded();
        List<WebElement> shouldAppear = new ArrayList<>(asList(
                pageTitle,
                infoBarContainer, searchButton, loginButton,
                cookiesButton, requestButton
        ));
        shouldAppear.forEach(e -> selenium.waitElementAfterJSCompleted(e));
        selenium.areDisplayed(shouldAppear.toArray(new WebElement[0]));
        return checkPageValidity(resource);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    public void clickSearchButton() { selenium.clickElementWithJavaScript(searchButton); }

    public void clickLoginButton() {
        selenium.clickElementWithJavaScript(loginButton);
    }

    public void clickCookiesButton() {
        selenium.clickElementWithJavaScript(cookiesButton);
    }

    public void clickRequestButton() {
        selenium.clickElementWithJavaScript(requestButton);
    }

    public void enterSearchInput(String value) {
        selenium.enterValue(searchInput, value);
        searchCriteria = value;
    }

    public boolean isContactRequestFormVisible() {
        return contentRequestModal.isDisplayed();
    }

    public boolean isInformationBarVisible() {
        return infoBarContainer.isDisplayed();
    }

    public void submitSearchInput() { searchInput.submit(); }

}
