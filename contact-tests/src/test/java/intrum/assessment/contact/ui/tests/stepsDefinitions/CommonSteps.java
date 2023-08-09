package intrum.assessment.contact.ui.tests.stepsDefinitions;

import intrum.assessment.contact.constants.ContactParams;
import intrum.assessment.contact.pageobjects.*;
import intrum.assessment.framework.pageobjects.IPageObject;
import intrum.assessment.framework.pageobjects.PageObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static intrum.assessment.contact.constants.ContactParams.LOGIN_URL;
import static intrum.assessment.contact.constants.ContactParams.SEARCH_URL;
import static intrum.assessment.contact.constants.CucumberParams.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {

    private final CommonPage commonPage;
    private final ContactPage contactPage;
    private final ContactRequestPage contactRequestPage;
    private final CookiesPage cookiesPage;
    private final CookieSettingsPage cookieSettingsPage;

    private final Map<IPageObject, List<String>> pageMapping;

    @Autowired
    public CommonSteps(CommonPage commonPage,
                       ContactPage contactPage,
                       ContactRequestPage contactRequestPage,
                       CookiesPage cookiesPage,
                       CookieSettingsPage cookieSettingsPage) {
        this.commonPage = commonPage;
        this.contactPage = contactPage;
        this.contactRequestPage = contactRequestPage;
        this.cookiesPage = cookiesPage;
        this.cookieSettingsPage = cookieSettingsPage;
        this.pageMapping = new HashMap<IPageObject, List<String>>() {{
            put(contactPage, singletonList(FORM_CONTACT));
            put(contactRequestPage, singletonList(FORM_CONTACT_REQUEST));
            put(cookiesPage, singletonList(FORM_COOKIES));
            put(cookieSettingsPage, singletonList(FORM_COOKIE_SETTINGS));
        }};
    }

    @Given("User navigate to Contact module")
    public void COMMON_given_navigateToModule() {
        commonPage.driver.get(ContactParams.PAGE_URL);
    }

    @When("User waits for {string} page to load")
    public void COMMON_given_directedToPage(String name) {
        switch (name) {
            case FORM_EXTERNAL_SEARCH ->
                    assertEquals((SEARCH_URL + contactPage.getSearchCriteria()), commonPage.getCurrentUrl());
            case FORM_EXTERNAL_LOGIN ->
                    assertTrue(commonPage.getCurrentUrl().startsWith(LOGIN_URL));
            default -> assertTrue(PageObject.fromMappingByName(pageMapping, name).isOpened());
        }
    }

    @When("User clicks {string} button")
    public void COMMON_when_user_clicks_button(String name) {
        switch (name) {
            case BUTTON_SEARCH -> contactPage.clickSearchButton();
            case BUTTON_LOGIN -> contactPage.clickLoginButton();
            case BUTTON_COOKIE_SETTINGS -> contactPage.clickCookiesButton();
            case BUTTON_ACCEPT_ALL_COOKIES -> cookiesPage.clickAcceptButton();
            case BUTTON_CONFIRM_MY_CHOISES -> cookieSettingsPage.clickConfirmButton();
            case BUTTON_REQUEST -> contactPage.clickRequestButton();
            case BUTTON_REQUEST_SUBMIT -> contactRequestPage.clickSubmitButton();
        }
    }

    @When("Ensure {string} page or area closed")
    public void COMMON_when_ensure_page_or_area_closed(String name) {
        switch (name) {
            case FORM_CONTACT_REQUEST -> assertFalse(contactRequestPage.isVisible());
            case FORM_COOKIES -> assertFalse(cookiesPage.isVisible());
            case FORM_COOKIE_SETTINGS -> assertFalse(cookieSettingsPage.isVisible());
        }
    }

    @When("User fills contact request data")
    public void COMMON_user_enters_data(DataTable table) throws Exception {
        List<List<String>> rows = table.asLists(String.class);
        // Skip header of datatable
        List<String> values = rows.get(1);
        // Map data to form fields from datatable
        ListIterator<String> it = values.listIterator();
        while (it.hasNext()) {
            int index = it.nextIndex();
            String value = it.next();
            String field = rows.get(0).get(index);
            switch (field) {
                case FIELD_NAME_SURNAME -> contactRequestPage.enterNameSurname(value);
                case FIELD_PERSON_CODE -> contactRequestPage.enterPersonCode(value);
                case FIELD_DOCUMENT_NUMBER -> contactRequestPage.enterDocumentNumber(value);
                case FIELD_PHONE -> contactRequestPage.enterPhone(value);
                case FIELD_EMAIL -> contactRequestPage.enterEmail(value);
                case FIELD_ADDRESS -> contactRequestPage.enterAddress(value);
                case FIELD_MEANING -> contactRequestPage.enterMeaning(value);
                case FIELD_REQUEST_TYPE -> contactRequestPage.selectRequestTypeOption(value);
            }
        }
    }

    @When("Ensure mandatory validation message appears for")
    public void COMMON_validation_message(DataTable table) throws Exception {
        List<List<String>> rows = table.asLists(String.class);
        // Skip header of datatable
        List<String> values = rows.get(1);
        // Map data to form fields from datatable
        ListIterator<String> it = values.listIterator();
        while (it.hasNext()) {
            int index = it.nextIndex();
            String value = it.next();
            String field = rows.get(0).get(index);
            switch (field) {
                case FIELD_NAME_SURNAME -> assertTrue(contactRequestPage.isNameSurnameInvalid());
                case FIELD_PERSON_CODE -> assertTrue(contactRequestPage.isPersonCodeInvalid());
                case FIELD_DOCUMENT_NUMBER -> assertTrue(contactRequestPage.isDocumentNumberInvalid());
                case FIELD_PHONE -> assertTrue(contactRequestPage.isPhoneInvalid());
                case FIELD_EMAIL -> assertTrue(contactRequestPage.isEmailInvalid());
                case FIELD_ADDRESS -> assertTrue(contactRequestPage.isAddressInvalid());
                case FIELD_MEANING -> assertTrue(contactRequestPage.isMeaningInvalid());
                case FIELD_REQUEST_TYPE -> assertTrue(contactRequestPage.isRequestTypeInvalid());
            }
        }
    }

}
