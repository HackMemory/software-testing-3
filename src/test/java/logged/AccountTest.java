package logged;

import misc.Properties;
import misc.Utils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.List;

public class AccountTest {
    private static final Logger logger = LoggerFactory.getLogger(AccountTest.class);
    List<RemoteWebDriver> drivers;
    SoftAssertions softly;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        drivers = Utils.getDrivers();
        softly = new SoftAssertions();
    }

    @AfterEach
    public void tearDown() {
        drivers.forEach(WebDriver::quit);
    }

    public void loginTest() {
        drivers.forEach(driver -> {
            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "started"
            );

            driver.get("https://www.youtube.com/");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.waitUntilPageLoads(driver, 10);

            Utils.getElementBySelector(driver, By.xpath("//a//yt-touch-feedback-shape//div")).click();

            Utils.getElementBySelector(driver, By.id("identifierId")).sendKeys(Properties.GOOGLE_LOGIN);
            Utils.getElementBySelector(driver, By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();
            Utils.getElementBySelector(driver, By.name("Passwd")).sendKeys(Properties.GOOGLE_PASSWORD);
            Utils.getElementBySelector(driver, By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();

            Utils.getElementBySelector(driver, By.xpath("//ytd-topbar-menu-button-renderer//img[@id='img']")).click();

            String login = Utils.getElementBySelector(driver, By.cssSelector("#channel-container > #email")).getText();
//            assertEquals(login, Properties.GOOGLE_LOGIN);
            softly.assertThat(login).isEqualTo(Properties.GOOGLE_LOGIN);

            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "successfully finished"
            );
        });
    }

    public void searchVideoTest(){
        drivers.forEach(driver -> {
            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "started"
            );

            driver.get("https://www.youtube.com/");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.waitUntilPageLoads(driver, 10);

            Utils.getElementBySelector(driver, By.name("search_query")).click();
            Utils.getElementBySelector(driver, By.name("search_query")).sendKeys("never gonna give you up");
            Utils.getElementBySelector(driver, By.id("search-icon-legacy")).click();

            Utils.getElementBySelector(driver, By.cssSelector(".ytd-item-section-renderer:nth-child(1) > #dismissible #video-title > .style-scope:nth-child(2)")).click();

            Utils.waitUntilPageLoads(driver, 10);
            String video_title = Utils.getElementBySelector(driver, By.xpath("//div[@id='title']//h1/yt-formatted-string")).getText();
//            assertEquals(video_title, "Rick Astley - Never Gonna Give You Up (Official Music Video)");
            softly.assertThat(video_title).isEqualTo("Rick Astley - Never Gonna Give You Up (Official Music Video)");

            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "successfully finished"
            );
        });
    }

    public void historyTest(){
        drivers.forEach(driver -> {
            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "started"
            );

            driver.get("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.waitUntilPageLoads(driver, 10);

            driver.get("https://www.youtube.com/feed/history");
            Utils.waitUntilPageLoads(driver, 10);

            Utils.getElementBySelector(driver, By.xpath("//a[@id=\"video-title\"]//yt-formatted-string[contains(text(), \"Rick Astley - Never Gonna Give You Up\")]"));
            int watched_count = driver.findElements(By.xpath("//a[@id=\"video-title\"]//yt-formatted-string[contains(text(), \"Rick Astley - Never Gonna Give You Up\")]")).size();
//            assertTrue(watched_count > 0);
            softly.assertThat(watched_count).isGreaterThan(0);

            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "successfully finished"
            );
        });
    }

    public void likeTest(){
        drivers.forEach(driver -> {
            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "started"
            );

            driver.get("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.waitUntilPageLoads(driver, 10);

            WebElement element = Utils.getElementBySelector(driver, By.xpath("//div[@id=\'segmented-like-button\']//ytd-toggle-button-renderer[@class=\'style-scope ytd-segmented-like-dislike-button-renderer\']//button"));
            String attribute = element.getAttribute("aria-pressed");
            if(attribute.equals("false")){
                Utils.getElementBySelector(driver, By.xpath("//div[@id=\'segmented-like-button\']//ytd-toggle-button-renderer[@class=\'style-scope ytd-segmented-like-dislike-button-renderer\']//button")).click();
            }

            attribute = element.getAttribute("aria-pressed");
//            assertEquals("true", attribute);
            softly.assertThat(attribute).isEqualTo("true");

            driver.get("https://www.youtube.com/playlist?list=LL");
            Utils.waitUntilPageLoads(driver, 10);
            int liked_count = driver.findElements(By.xpath("//a[@id=\'video-title\' and contains(text(), \"Rick Astley - Never Gonna Give You Up\")]")).size();
//            assertTrue(liked_count > 0);
            softly.assertThat(liked_count).isGreaterThan(0);

            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "successfully finished"
            );
        });
    }

    public void subscribeTest(){
        drivers.forEach(driver -> {
            logger.info(
                    "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                            "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                            "started"
            );

            driver.get("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.waitUntilPageLoads(driver, 10);

            int pressed = driver.findElements(By.xpath("//div[@id=\"subscribe-button\" and @class=\"style-scope ytd-watch-metadata\"]//ytd-subscribe-button-renderer//span[contains(text(), \"Подписаться\")]")).size();
            if(pressed == 1){
                Utils.getElementBySelector(driver, By.xpath("//div[@id=\"subscribe-button\" and @class=\"style-scope ytd-watch-metadata\"]//ytd-subscribe-button-renderer//span[contains(text(), \"Подписаться\")]")).click();
            }

            pressed = driver.findElements(By.xpath("//div[@id=\"subscribe-button\" and @class=\"style-scope ytd-watch-metadata\"]//ytd-subscribe-button-renderer//span[contains(text(), \"Подписаться\")]")).size();
//            assertEquals(0, pressed);
            softly.assertThat(pressed).isEqualTo(0);


            logger.info(
                    "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                            "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                            "successfully finished"
            );
        });
    }

    public void watchLaterTest(){
        drivers.forEach(driver -> {
            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "started"
            );

            driver.get("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            driver.manage().window().setSize(new Dimension(1078, 713));
            Utils.waitUntilPageLoads(driver, 10);

            Utils.getElementBySelector(driver, By.xpath("//ytd-button-renderer[.//span[contains(text(),'Сохранить')]]")).click();
            WebElement element = Utils.getElementBySelector(driver, By.xpath("//tp-yt-paper-checkbox[.//yt-formatted-string[contains(text(), \'Смотреть позже\')]]"));
            String pressed = element.getAttribute("aria-checked");

            if(pressed.equals("false")){
                Utils.getElementBySelector(driver, By.xpath("//tp-yt-paper-checkbox[@id=\'checkbox\']//yt-formatted-string[contains(text(), \'Смотреть позже\')]")).click();
            }

            pressed = element.getAttribute("aria-checked");
//            assertEquals("true", pressed);
            softly.assertThat(pressed).isEqualTo("true");


            driver.get("https://www.youtube.com/playlist?list=WL");
            Utils.waitUntilPageLoads(driver, 10);

            Utils.getElementBySelector(driver, By.xpath("//a[@id=\'video-title\' and contains(text(), \"Rick Astley - Never Gonna Give You Up\")]"));
            int watched_count = driver.findElements(By.xpath("//a[@id=\'video-title\' and contains(text(), \"Rick Astley - Never Gonna Give You Up\")]")).size();
//            assertEquals(1, watched_count);
            softly.assertThat(watched_count).isEqualTo(1);


            logger.info(
                "Test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " +
                "Browser: " + driver.getCapabilities().getCapability("browserName").toString() + ", " +
                "successfully finished"
            );
        });
    }

    @Test
    public void allTest(){
        this.loginTest();
        this.searchVideoTest();
        this.historyTest();
        this.likeTest();
        this.subscribeTest();
        this.watchLaterTest();

        softly.assertAll();
    }
}
