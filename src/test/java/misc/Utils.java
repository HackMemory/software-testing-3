package misc;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static List<RemoteWebDriver> getDrivers() throws MalformedURLException {
        List<RemoteWebDriver> drivers = new ArrayList<>();
        drivers.add(new RemoteWebDriver(new URL(Properties.REMOTE_URL), Utils.chromeOptions()));
        drivers.add(new RemoteWebDriver(new URL(Properties.REMOTE_URL), Utils.firefoxOptions()));
        drivers.add(new RemoteWebDriver(new URL(Properties.REMOTE_URL), Utils.operaOptions()));

        return drivers;
    }

    public static ChromeOptions chromeOptions(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("enableVNC", true);
        chromeOptions.setCapability("browserName", "chrome");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "ru-RU");
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

    public static FirefoxOptions firefoxOptions(){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("enableVNC", true);
        firefoxOptions.setCapability("browserName", "firefox");
        firefoxOptions.addPreference("intl.accept_languages", "ru-RU");

        return firefoxOptions;
    }

    public static OperaOptions operaOptions(){
        OperaOptions operaOptions = new OperaOptions();
        operaOptions.setCapability("enableVNC", true);
        operaOptions.setCapability("browserName", "opera");
        operaOptions.addArguments("--lang=ru-RU");

        return operaOptions;
    }


    public static WebElement getElementBySelector(WebDriver driver, By selector) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public static void waitUntilPageLoads(WebDriver driver, long timeout) {
        WebDriverWait waitDriver = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        waitDriver.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}
