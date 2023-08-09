package intrum.assessment.framework.config;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Configuration
@Scope(value = SCOPE_CUCUMBER_GLUE)
public class SeleniumConfig {

	@Autowired
	private Environment environment;

	@Autowired
	private WebDriver driver;

	public SeleniumConfig() {}

	public WebDriver getDriver() {
		return this.driver;
	}

	public void createDriver() {}

	public void quitDriver() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}
}
