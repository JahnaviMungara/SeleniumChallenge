import heroku.testing.baseTest.Base;
import heroku.testing.pages.BrokenImagesPage;
import org.testng.annotations.Test;

import java.util.List;

public class ImageValidatorTests extends Base {

    @Test
    public void validateImage() throws Throwable {
        BrokenImagesPage brokenImagesPage = new BrokenImagesPage(driver);
        brokenImagesPage.getPage();
        List<String> imageUrls = brokenImagesPage.getImageUrl();
        for (String url: imageUrls) {
            brokenImagesPage.validateImage(url);
        }
    }

}
