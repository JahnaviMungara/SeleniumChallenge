package heroku.testing.pages;

import heroku.testing.utilities.SafeActions;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class BrokenImagesPage extends SafeActions {
    public BrokenImagesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.example img")
    public List<WebElement> images;

    HomePage homePage = new HomePage(driver);

    public void getPage() {
        homePage.getLinkPage("/broken_images");
    }

    public void validateImage(String path) throws Throwable {
        Response response = sendGetRequest(path);
        if (response.getStatusCode() == 200) {
            System.out.println(path +" Image displayed");
        } else {
            System.out.println(path +" Image broken");
        }

    }

    public List<String> getImageUrl() {
        List<String> imageUrls = new ArrayList<String>();
        for (WebElement image: images) {
            String url = getAttributeValue(image, "src");
            imageUrls.add(url);
        }
        return imageUrls;
    }

}
