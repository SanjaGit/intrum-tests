package intrum.assessment.framework.config;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
public class WebDriverManager {

    @Autowired
    private Environment environment;

    @Bean
    @Scope(value = SCOPE_CUCUMBER_GLUE)
    public WebDriver webDriverFactory() {
        return WebDriverFactory.factory(environment, "chrome");
    }
}
