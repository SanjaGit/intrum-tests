package intrum.assessment.framework.pageobjects;

import io.netty.util.internal.ObjectCleaner;
import org.springframework.boot.env.YamlPropertySourceLoader;
import intrum.assessment.framework.constants.Params;
import intrum.assessment.framework.constants.Settings;
import intrum.assessment.framework.util.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
@Scope(value = SCOPE_CUCUMBER_GLUE)
public abstract class PageObject implements IPageObject {

	public final WebDriver driver;

	public final WebDriverWait wait;

	public final Log log;

	static int index = System.getProperty("user.dir").lastIndexOf(File.separator);
	static String resourcesDir = System.getProperty("user.dir").substring(0, index) + File.separator + "common-util";

	protected static String downloadDir = resourcesDir + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "downloads" + File.separator;

	@Autowired
	public PageObject(WebDriver driver) {
		this.driver = driver;
		this.wait = (WebDriverWait)
				new WebDriverWait(driver, Duration.ofSeconds(Settings.TIMEOUT_VISIBILITY))
						.ignoring(StaleElementReferenceException.class)
						.ignoring(NoSuchElementException.class);
		this.log = new Log();
		PageFactory.initElements(driver, this);
	}

	public boolean checkPageValidity(Resource resource) {
		YamlPropertySourceLoader ypsl = new YamlPropertySourceLoader();
		Map<String, Object> map;
		try {
			PropertySource ps = ypsl.load(resource.getFilename(), resource).get(0);
			map = (Map<String, Object>)ps.getSource();
		} catch (IOException ex) {
			map = new HashMap<>();
		}
		// Iterate over unique parameter name list
		List<String> values = map.keySet().stream()
				.map(val -> val.substring(0, val.lastIndexOf(".")))
				.collect(Collectors.toSet()).stream()
				.toList();
		for (String value : values) {
			String xpath = value + "." + Params.VALID_XPATH;
			if (map.containsKey(xpath)) {
				String xpathValue = map.get(xpath).toString();
				String text = value + "." + Params.VALID_TEXT;
				String size = value + "." + Params.VALID_SIZE;
				String link = value + "." + Params.VALID_LINK;
				WebElement element = driver.findElement(By.xpath(xpathValue));
				if (map.containsKey(text)) {
					String textValue = map.get(text).toString();
					return !element.getText().equals(textValue);
				}
				if (map.containsKey(size)) {
					String sizeValue = map.get(size).toString();
					return !element.getSize().toString().equals(sizeValue);
				}
				if (map.containsKey(link)) {
					String linkValue = map.get(link).toString();
					return !element.getAttribute("href").equals(linkValue);
				}
			}
		}
		return true;
	}

	public static IPageObject fromMappingByName(Map<IPageObject, List<String>> mapping, String name) {
		IPageObject result = null;
		for (Map.Entry<IPageObject, List<String>> entry : mapping.entrySet()) {
			IPageObject page = entry.getKey();
			List<String> values = entry.getValue().stream().map(String::toLowerCase).toList();
			if (values.contains(name.toLowerCase())) {
				result = page;
			}
		}
		assertNotNull(result);
		return result;
	}

	@Override
	public boolean isOpened() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return false;
	}
}

