package ProjectFakeStore;

import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTests extends BaseTest {

    String productUrl = baseUrl + "/wspinaczka-via-ferraty/";
    String categoryUrl = baseUrl + "/product-category/windsurfing/";
    String productId = "40";


    @Test
    public void buyOneProductWithoutAccountTest() {

        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();

        OrderReceivedPage orderReceivedPage = productPage.addToCart()
                .viewCart()
                .goToCheckOut()
                .fillOutValidDetails()
                .submitPaymentDetails()
                .acceptTerms()
                .placeOrder();

        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "The order was not succesfully placed.");
    }

}
