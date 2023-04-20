package guest;

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

public class SearchChannelPopularVideoTest {
    private static final Logger logger = LoggerFactory.getLogger(SearchChannelPopularVideoTest.class);
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
    public void searchTest() {
        drivers.forEach(driver -> {
            driver.get("https://www.youtube.com/");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.getElementBySelector(driver, By.name("search_query")).click();
            Utils.getElementBySelector(driver, By.name("search_query")).sendKeys("rick astley");
            Utils.getElementBySelector(driver, By.cssSelector("#search-icon-legacy > .style-scope:nth-child(1)")).click();

            Utils.waitUntilPageLoads(driver, 10);

            Utils.getElementBySelector(driver, By.cssSelector(".ytd-channel-renderer > #img")).click();
            Utils.getElementBySelector(driver, By.cssSelector(".style-scope:nth-child(5) > .tab-content")).click();
            Utils.getElementBySelector(driver, By.cssSelector(".grid-4-columns > #primary .style-scope:nth-child(2) > #text")).click();

            String title = Utils.getElementBySelector(driver, By.cssSelector(".iron-selected:nth-child(2) > #text")).getText();
            assertEquals(title, "По популярности");

            logger.info(
                    "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                    "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                    "successfully finished"
            );
        });
    }
}
