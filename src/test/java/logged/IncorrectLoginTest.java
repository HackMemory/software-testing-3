package logged;

import guest.MusicTest;
import misc.Properties;
import misc.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncorrectLoginTest {
    private static final Logger logger = LoggerFactory.getLogger(IncorrectLoginTest.class);
    List<RemoteWebDriver> drivers;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        drivers = Utils.getDrivers();
    }

    @AfterEach
    public void tearDown() {
        drivers.forEach(WebDriver::quit);
    }

    @Test
    public void loginTest() {
        drivers.forEach(driver -> {
            driver.get("https://www.youtube.com/");
            driver.manage().window().setSize(new Dimension(1078, 713));

            Utils.getElementBySelector(driver, By.xpath("//a//yt-touch-feedback-shape//div")).click();
            Utils.getElementBySelector(driver, By.id("identifierId")).sendKeys(Properties.GOOGLE_INCORRECT_LOGIN);
            Utils.getElementBySelector(driver, By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();

            String title = Utils.getElementBySelector(driver, By.cssSelector(".o6cuMc")).getText();
            assertEquals(title, "Не удалось найти аккаунт Google.");

            logger.info(
                    "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                    "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                    "successfully finished"
            );
        });
    }
}
