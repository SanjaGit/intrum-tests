package intrum.assessment.contact.pageobjects;

import intrum.assessment.framework.pageobjects.PageObject;
import intrum.assessment.framework.services.SeleniumService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static intrum.assessment.framework.constants.Params.SKIP;
import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static java.util.Arrays.asList;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class ContactRequestPage extends PageObject {

    @Autowired
    private SeleniumService selenium;

    @Autowired
    public ContactRequestPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='slide']")
    private WebElement modal;

    @FindBy(xpath = "//div[@id='slide']//div[@class='preamble']//h1")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'vārdsuzvārds')]")
    private WebElement nameSurnameInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'vārdsuzvārds')]//input")
    private WebElement nameSurnameInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'personaskods')]")
    private WebElement personCodeInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'personaskods')]//input")
    private WebElement personCodeInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'lietasnumursnavobligāts')]")
    private WebElement documentNumberInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'lietasnumursnavobligāts')]//input")
    private WebElement documentNumberInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'kontakttālrunis')]")
    private WebElement phoneInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'kontakttālrunis')]//input")
    private WebElement phoneInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'epastaadrese')]")
    private WebElement emailInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'epastaadrese')]//input")
    private WebElement emailInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'adrese')]")
    private WebElement addressInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'adrese')]//input")
    private WebElement addressInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'komentāraiebildumubūtība')]")
    private WebElement meaningInput;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'komentāraiebildumubūtība')]//textarea")
    private WebElement meaningInputField;

    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'kāvēlossaņemtatbildi')]")
    private WebElement requestTypeSelect;
    @FindBy(xpath = "//div[@id='slide']//div[contains(@class,'kāvēlossaņemtatbildi')]//select")
    private WebElement requestTypeSelectField;

    @FindBy(xpath = "//div[@id='slide']//button[@title='Close']")
    private WebElement btnClose;
    @FindBy(xpath = "//div[@id='slide']//input[@name='__next']")
    private WebElement btnSubmit;

    private static final String VALIDATION_XPATH = "//span[@class='field-validation-error']/span";
    public static String DROPDOWN_OPTION = "//option[contains(text(),'%s')]";

    @Override
    public boolean isOpened() {
        selenium.waitForPageLoaded();
        List<WebElement> shouldAppear = new ArrayList<>(asList(
                pageTitle,
                nameSurnameInput, personCodeInput, documentNumberInput, phoneInput,
                emailInput, addressInput, meaningInput, requestTypeSelect,
                btnClose, btnSubmit
        ));
        shouldAppear.forEach(e -> selenium.waitElementAfterJSCompleted(e));
        selenium.areDisplayed(shouldAppear.toArray(new WebElement[0]));
        return pageTitle.isDisplayed();
    }

    @Override
    public boolean isVisible() {
        return modal.isDisplayed();
    }

    public void clickSubmitButton() {
        btnSubmit.submit();
    }

    public void enterNameSurname(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        nameSurnameInputField.clear();
        nameSurnameInputField.sendKeys(value);
    }

    public void enterPersonCode(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        personCodeInputField.clear();
        personCodeInputField.sendKeys(value);
    }

    public void enterDocumentNumber(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        documentNumberInputField.clear();
        documentNumberInputField.sendKeys(value);
    }

    public void enterPhone(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        phoneInputField.clear();
        phoneInputField.sendKeys(value);
    }

    public void enterEmail(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        emailInputField.clear();
        emailInputField.sendKeys(value);
    }

    public void enterAddress(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        addressInputField.clear();
        addressInputField.sendKeys(value);
    }

    public void enterMeaning(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        selenium.waitForJStoLoad();
        meaningInputField.clear();
        meaningInputField.sendKeys(value);
    }

    public void selectRequestTypeOption(String value) {
        if (value == null || SKIP.equalsIgnoreCase(value)) return;
        Select select = new Select(requestTypeSelectField);
        select.selectByVisibleText(value);
    }

    public boolean isNameSurnameInvalid() {
        WebElement error = nameSurnameInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isPersonCodeInvalid() {
        WebElement error = personCodeInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isDocumentNumberInvalid() {
        WebElement error = documentNumberInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isPhoneInvalid() {
        WebElement error = phoneInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isEmailInvalid() {
        WebElement error = emailInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isAddressInvalid() {
        WebElement error = addressInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isMeaningInvalid() {
        WebElement error = meaningInput.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

    public boolean isRequestTypeInvalid() {
        WebElement error = requestTypeSelect.findElement(By.xpath(VALIDATION_XPATH));
        return error.isDisplayed();
    }

}
