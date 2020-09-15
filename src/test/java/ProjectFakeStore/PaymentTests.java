package ProjectFakeStore;

import PageObjects.MyOrdersPage;
import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTests extends BaseTest {

    @Test
    public void buyOneProductWithoutAccountTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();

        productPage.addToCart()
                .viewCart()
                .goToCheckOut()
                .typeFirstName(testData.getCustomer().getName())
                .typeLastName(testData.getCustomer().getLastName())
                .chooseCountry(testData.getAddress().getCountryCode())
                .typeAddress(testData.getAddress().getStreet())
                .typePostalCode(testData.getAddress().getPostalCode())
                .typeCity(testData.getAddress().getCity())
                .typePhone(testData.getContact().getPhone())
                .typeEmail(testData.getContact().getEmail())
                .typeCardNumber(testData.getCard().getNumber())
                .typeCardExpirationDate(testData.getCard().getExpirationDate())
                .typeCvcCode(testData.getCard().getCvc())
                .acceptTerms()
                .placeOrder();

        OrderReceivedPage orderReceivedPage = new OrderReceivedPage(driver);
        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "The order was not successfully placed.");
    }

    @Test
    public void buyOneProductAndSignUpTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();

        int amountOfOrderedProducts = productPage.addToCart()
                .viewCart()
                .goToCheckOut()
                .typeFirstName(testData.getCustomer().getName())
                .typeLastName(testData.getCustomer().getLastName())
                .chooseCountry(testData.getAddress().getCountryCode())
                .typeAddress(testData.getAddress().getStreet())
                .typePostalCode(testData.getAddress().getPostalCode())
                .typeCity(testData.getAddress().getCity())
                .typePhone(testData.getContact().getPhone())
                .typeEmail(testData.getContact().getEmail())
                .typeCardNumber(testData.getCard().getNumber())
                .typeCardExpirationDate(testData.getCard().getExpirationDate())
                .typeCvcCode(testData.getCard().getCvc())
                .createNewAccount()
                .typePassword(testData.getPassword().getPassword())
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

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
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
                orderStatus, "Failed to sign in from payment page and to complete the order simultaneously.");
    }
}