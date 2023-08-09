package intrum.assessment.contact.ui.tests.stepsDefinitions;

import intrum.assessment.contact.pageobjects.ContactPage;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactSteps {

    private final ContactPage contactPage;

    @Autowired
    public ContactSteps(ContactPage contactPage) {
        this.contactPage = contactPage;
    }

    @When("User fills search criteria {string}")
    public void CONTACT_user_enters_search_criteria(String value) {
        contactPage.enterSearchInput(value);
    }

    @When("User performs searching")
    public void CONTACT_user_performs_searching() {
        contactPage.submitSearchInput();
    }

    @When("Ensure Contact Request form submitted")
    public void CONTACT_ensure_request_submitted() {
        assertFalse(contactPage.isContactRequestFormVisible());
    }

    @When("Ensure Info Bar is disappeared")
    public void CONTACT_ensure_info_bar_disappeared() {
        assertFalse(contactPage.isInformationBarVisible());
    }

}
