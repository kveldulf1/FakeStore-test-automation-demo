package ProjectFakeStore;

import PageObjects.MyAccountPage;
import PageObjects.MyOrdersPage;
import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

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
        assertTrue(isOrderSuccessful, "The order was not successfully placed.");
    }

    @RepeatedTest(19)
    public void buyOneProductAndSignUpTest() throws InterruptedException{

        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();

        int amountOfOrderedProducts = productPage.addToCart()
                .viewCart()
                .goToCheckOut()
                .fillOutRegistrationDetails("Egill", "Skallagrimsson",
                        "abc9@postur.is", "Kveldulfsvegur", "11-123",
                        "Mosfell", "600321666")
                .createNewAccount()
                .setPassword("ComplicatedPassword720!")
                .submitPaymentDetails()
                .acceptTerms()
                .placeOrder()
                .goToMyAccount()
                .goToMyOrders()
                .getNumberOfRows();

        assertTrue(amountOfOrderedProducts > 0,
                "Failed to place the order with simultaneous new account registration.");

        MyOrdersPage myOrdersPage = new MyOrdersPage(driver);

        myOrdersPage
                .goToMyAccount()
                .deleteAccount();

    }

}
