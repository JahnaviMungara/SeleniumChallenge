package heroku.testing.utilities;

import heroku.testing.pages.HomePage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class SafeActions {
    public WebDriver driver;
    public SafeActions(WebDriver driver){
        this.driver = driver;
    }

    public void scrollToElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void WaitforAllElementVisibility(List<WebElement> elements) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(20));
        w.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void WaitforElementVisibility(WebElement element) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
        w.until(ExpectedConditions.visibilityOf(element));
    }


    public void switchToWindows() {
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        String parentId = it.next();
        String childId = it.next();
        driver.switchTo().window(childId);
    }
    public void mouseHover(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public void clickAnElement(WebElement element)
    {
        WaitforElementVisibility(element);
        element.click();

    }
    public void sendData(WebElement element,String value) {
        WaitforElementVisibility(element);
        element.sendKeys(value);
    }

    /**
     * Get dynamic locator resolved to normal one
     *
     * @param locator     - locator that needs to be replaced
     * @param dynamicText - text that is dynamic in the locator
     * @return By - new locator after placing required text in the locator string
     */
    public By getNewLocator(By locator, String dynamicText) {
        String locatorType = locator.toString().split(": ")[0].split("\\.")[1];
        String newLocatorString = String.format(locator.toString().split(": ")[1], dynamicText);
        switch (locatorType) {
            case "xpath":
                locator = By.xpath(newLocatorString);
                break;
            case "cssSelector":
                locator = By.cssSelector(newLocatorString);
                break;
            case "id":
                locator = By.id(newLocatorString);
                break;
            case "className":
                locator = By.className(newLocatorString);
                break;
            case "name":
                locator = By.name(newLocatorString);
                break;
            case "linkText":
                locator = By.linkText(newLocatorString);
                break;
            case "partialLinkText":
                locator = By.partialLinkText(newLocatorString);
                break;
            case "tagName":
                locator = By.tagName(newLocatorString);
                break;
            default:
                // if (LOGGER.isErrorEnabled()) {
                //   LOGGER.error("Invalid locator type");
                  // }
                break;
        }
        return locator;

    }
    public String getDataFromPropertyFile(String value) throws Throwable {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\configuration.properties");
        properties.load(fis);
        return properties.getProperty(value);
    }


    public Response sendGetRequest(String pathParam) throws Throwable {
        RestAssured.baseURI = getDataFromPropertyFile("serviceUrl");
        RequestSpecification request = RestAssured.given();
        return request.get(pathParam);
    }

    public String getAttributeValue(WebElement element, String key){
        return element.getAttribute(key).toLowerCase();
    }

}

