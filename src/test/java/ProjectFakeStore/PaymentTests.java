package ProjectFakeStore;

import PageObjects.CheckoutPage;
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
                .typeCardNumber(testData.getCard().getNumber()) // Due to a Selenium bug, PaymentTests are not working properly with Firefox
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
                .typeCardNumber(testData.getCard().getNumber()) // Due to a Selenium bug, PaymentTests are not working properly with Firefox
                .typeCardExpirationDate(testData.getCard().getExpirationDate())
                .typeCvcCode(testData.getCard().getCvc())
                .createNewAccount()
                .typePassword(testData.getPassword().getPassword())
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
                .enterUserName("kveldulf@postur.is")
                .enterPassword("ComplicatedPassword720!")
                .useLogInButton()
                .enterEmail("kveldulf@postur.is")
                .submitPaymentDetails() // Due to a Selenium bug, PaymentTests are not working properly with Firefox
                .acceptTerms()
                .placeOrder()
                .getOrderStatus();

        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.",
                orderStatus, "Failed to sign in from payment page and to complete the order simultaneously.");
    }

    @Test
    public void checkoutFormValidationTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();

        productPage.addToCart()
                .viewCart()
                .goToCheckOut()
                .fillOutPersonalData("", "", "", "", "", "", "")
                .submitPaymentDetails()
                .acceptTerms()
                .placeOrderAndDisplayErrors();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        String errorMessage = checkoutPage.getErrorMessage();

        assertAll(
                () -> assertTrue(errorMessage.contains(checkoutPage.returnNameErrorMessage()),
                        "Displayed validation error message for the first name field was not as expected."),
                () -> assertTrue(errorMessage.contains(checkoutPage.returnLastNameErrorMessage()),
                        "Displayed validation error message for the last name field was not as expected."),
                () -> assertTrue(errorMessage.contains(checkoutPage.returnStreetErrorMessage()),
                        "Displayed validation error message for the street field was not as expected."),
                () -> assertTrue(errorMessage.contains(checkoutPage.returnZipCodeErrorMessage()),
                        "Displayed validation error message for the zip code field was not as expected."),
                () -> assertTrue(errorMessage.contains(checkoutPage.returnCityErrorMessage()),
                        "Displayed validation error message for the city field was not as expected."),
                () -> assertTrue(errorMessage.contains(checkoutPage.returnPhoneNumberErrorMessage()),
                        "Displayed validation error message for the phone number field was not as expected."),
                () -> assertTrue(errorMessage.contains(checkoutPage.returnEmailErrorMessage()),
                        "Displayed validation error message for the email field was not as expected.")
        );
    }
}