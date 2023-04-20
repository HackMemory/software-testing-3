package guest;

import misc.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrendVideosTest {
    private static final Logger logger = LoggerFactory.getLogger(TrendVideosTest.class);
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
    public void trendVideos() {
        drivers.forEach(driver -> {
            driver.get("https://www.youtube.com/");
            driver.manage().window().setSize(new Dimension(1075, 709));
            Utils.getElementBySelector(driver, By.id("guide-icon")).click();
            Utils.getElementBySelector(driver, By.cssSelector(".style-scope:nth-child(4) > #items > .style-scope:nth-child(1) > #endpoint .title")).click();
            String title = Utils.getElementBySelector(driver, By.cssSelector("#meta > #channel-name > #container #text")).getText();
            assertEquals(title, "В тренде");

            logger.info(
                    "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                    "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                    "successfully finished"
            );
        });
    }
}
