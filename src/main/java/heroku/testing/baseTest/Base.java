package heroku.testing.baseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class Base {
    public WebDriver driver;//private- parallel

    @BeforeMethod
    public void webDriverInitialization() throws Throwable {

        //log.info("Driver initialization");

        String browserName = getDataFromPropertyFile("Browser");
        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");
    }

    public String getDataFromPropertyFile(String value) throws Throwable {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\configuration.properties");
        properties.load(fis);
        return properties.getProperty(value);
    }

    @AfterMethod
    public void webDriverClose() {
        driver.quit();
    }

}
