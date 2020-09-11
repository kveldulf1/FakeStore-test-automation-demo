package ProjectFakeStore;

import PageObjects.MyOrdersPage;
import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTests extends BaseTest {

    String productUrl = baseUrl + "/wspinaczka-via-ferraty/";

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

    @Test
    public void buyOneProductAndSignUpTest() {

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
        myOrdersPage.goToMyAccount().deleteAccount();

    }

    @Test
    public void payAndSignInFromPaymentPageTest() {

        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();

        String orderStatus = productPage.addToCart()
                .viewCart()
                .goToCheckOut()
                .goToSignInSection()
                .enterUserName("abc123@postur.is")
                .enterPassword("Egil123!!!")
                .useLogInButton()
                .enterEmail("abc123@postur.is")
                .submitPaymentDetails()
                .acceptTerms()
                .placeOrder()
                .getOrderStatus();

        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.",
                orderStatus, "Failed to sign from payment page and to complete the order.");
    }
}