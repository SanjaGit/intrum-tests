package intrum.assessment.framework.config;

public interface IWebDriverFactory {
    void performOnCleanData();
    void performOnFailure(String scenarioName);
    void performOnQuit();
}
