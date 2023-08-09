package intrum.assessment.contact.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({
        "intrum.assessment.framework.config",
        "intrum.assessment.framework.pageobjects",
        "intrum.assessment.framework.services",
        "intrum.assessment.framework.util",
        "intrum.assessment.contact.pageobjects",
        "intrum.assessment.contact.testcontext",
        "intrum.assessment.contact.ui.tests.runners",
        "intrum.assessment.contact.ui.tests.stepsDefinitions"
})
@PropertySource(value = "classpath:application.properties")
public class SpringContextConfig {
}
