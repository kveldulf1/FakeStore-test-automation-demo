package ProjectFakeStore;

import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTests extends BaseTest {

    @Test
    public void buyOneProductWithoutAccountTest() {

        ProductPage productPage = new ProductPage(driver).goToClimbingProduct();
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
