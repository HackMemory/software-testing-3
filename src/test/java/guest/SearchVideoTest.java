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

public class SearchVideoTest {
  private static final Logger logger = LoggerFactory.getLogger(SearchVideoTest.class);
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
  public void searchVideo() {
    drivers.forEach(driver -> {
      driver.get("https://www.youtube.com/");
      driver.manage().window().setSize(new Dimension(1075, 709));
      driver.findElement(By.name("search_query")).click();
      Utils.getElementBySelector(driver, By.name("search_query")).sendKeys("never gonna give you up");
      Utils.getElementBySelector(driver, By.id("search-icon-legacy")).click();
      Utils.getElementBySelector(driver, By.xpath("//a[@id='video-title']")).click();

      String title = Utils.getElementBySelector(driver, By.cssSelector(".style-scope:nth-child(4) > .ytd-watch-metadata:nth-child(1)")).getText();
      assertEquals(title, "Rick Astley - Never Gonna Give You Up (Official Music Video)");

      logger.info(
              "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
              "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
              "successfully finished"
      );
    });
  }
}
