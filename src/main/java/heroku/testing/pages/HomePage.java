package heroku.testing.pages;

import heroku.testing.utilities.SafeActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends SafeActions {
    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public final By links = By.cssSelector("div ul li a[href=\"%s\"]");

    public void getLinkPage(String linkText){
        By linkName = getNewLocator(links,linkText);
        WebElement linkElement = driver.findElement(linkName);
        clickAnElement(linkElement);
    }
}
