package intrum.assessment.contact.ui.tests.runners;

import intrum.assessment.contact.config.SpringContextConfig;
import intrum.assessment.contact.types.Contact;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@Category(Contact.class)
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:ui/features/Contact.feature"},
        glue = {"intrum.assessment.contact.ui.tests.stepsDefinitions",
                "intrum.assessment.contact"},
        tags = "@contact")
@ContextConfiguration(classes = SpringContextConfig.class)
public class TestRunnerContact {
}
