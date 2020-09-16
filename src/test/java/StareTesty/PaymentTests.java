package StareTesty;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTests {

    WebDriver driver;
    WebDriverWait wait;

    By addToCartButton = By.cssSelector("button[name='add-to-cart']");
    By passwordField = By.cssSelector("input#account_password");
    By blockedUI = By.cssSelector("div.blockUI.blockOverlay");
    By placeOrderButton = By.cssSelector("button#place_order");
    By cardNumberInput = By.cssSelector("input[name='cardnumber']");
    By nameField = By.cssSelector("input[name='billing_first_name']");
    By lastNameField = By.cssSelector("input[name='billing_last_name']");
    By mobileNumberField = By.cssSelector("input#billing_phone");
    By cityField = By.cssSelector("input#billing_city");
    By postcodeField = By.cssSelector("input#billing_postcode");
    By billingAddressField = By.cssSelector("input#billing_address_1");
    By emailField = By.cssSelector("input#billing_email");
    By summaryProductName = By.cssSelector(".product-name>a");
    By summaryOrderNumber = By.cssSelector("li.order>strong");
    By summaryDate = By.cssSelector("li.date>strong");
    By summaryTotalPrice = By.cssSelector("li.total>strong");
    By summaryPaymentMethod = By.cssSelector("li.method>strong");
    By summaryOrderedProductsQuantity = By.cssSelector("strong.product-quantity");
    By deleteAccountButton = By.cssSelector("a.delete-me");
    By errorList = By.cssSelector("ul.woocommerce-error");
    By usernameField = By.cssSelector("input#username");
    By passwordFieldSignIn = By.cssSelector("input#password");
    By myAccountButton = By.cssSelector("li.my-account.menu-item-201");
    By myOrdersButton = By.cssSelector("li.woocommerce-MyAccount-navigation-link--orders>a");
    By viewCartButton = By.cssSelector("li.menu-item-200>a");
    By checkoutButton = By.cssSelector("a.checkout-button");
    By cardCVCField = By.cssSelector("input[name='cvc']");
    By cardExpDateField = By.cssSelector("input[name='exp-date']");
    By placedOrderRows = By.cssSelector("div.woocommerce-MyAccount-content>table>tbody");
    By orderStatusInfo = By.cssSelector("p.woocommerce-notice");


    @BeforeEach
    public void driverSetup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        driver.navigate().to("https://fakestore.testelka.pl");
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();

    }

    @AfterEach
    public void driverQuit() {

        driver.close();
        driver.quit();

    }

    @Test
    public void buyOneProductWithoutAccountTest() {

        buyOneProductNoSignUp();
        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.",
                getOrderStatus(), "Failed to place the order without registration of a new account.");

    }

    @Test
    public void buyOneProductAndSignUpTest() {

        buyOneProductAndSignUp();
        assertTrue(driver.findElements(placedOrderRows).size() > 0,
                "Failed to place the order with simultaneous new account registration.");

        deleteMyAccount();
    }

    @Test
    public void registeredUserCanSeePlacedOrdersTest() {

        buyOneProductAndSignUp();
        goToMyOrders();
        assertTrue(driver.findElements(placedOrderRows).size() > 0,
                "User was not able to see his orders in \"Moje Zamówienia\" section.");
        deleteMyAccount();
    }

    @RepeatedTest(10)
    public void paymentPageSignInAndPayTest() {

        selectProductAndGoToPaymentPage();
        driver.findElement(By.cssSelector("a.showlogin")).click();

        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys("egil@postur.is");
        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldSignIn)).sendKeys("Egil123!!!");
        driver.findElement(By.cssSelector("button.woocommerce-form-login__submit")).click();

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys("egil@postur.is");
        submitPaymentDetails();
        acceptTerms();
        placeOrder();
        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.",
                getOrderStatus(), "Failed to sign from payment page and to complete the order.");

    }

    @Test
    public void orderSummaryTest() {

        buyOneProductNoSignUp();

        String expectedProductName = "Wspinaczka Via Ferraty";
        String actualProductName = driver.findElement(summaryProductName).getText();

        String expectedOrderNumber = driver.findElement(summaryOrderNumber).getText();
        int orderNumber = Integer.parseInt(expectedOrderNumber);

        String expectedDate = getCurrentDate();
        String actualDate = driver.findElement(summaryDate).getText();

        String expectedPrice = "2 799,00 zł";
        String actualPrice = driver.findElement(summaryTotalPrice).getText();

        String expectedPaymentMethod = "Karta debetowa/kredytowa (Stripe)";
        String actualPaymentMethod = driver.findElement(summaryPaymentMethod).getText();

        String expectedProductQuantity = "× 1";
        String actualQuantity = driver.findElement(summaryOrderedProductsQuantity).getText();

        assertAll(
                () -> assertEquals(expectedProductName, actualProductName,
                        "Product's name in the summary is different than name of the product which was added to cart."),
                () -> assertTrue(orderNumber > 0, "The order's number is not bigger than 0"),
                () -> assertEquals(expectedDate, actualDate, "The date in the summary is not as expected."),
                () -> assertEquals(expectedPrice, actualPrice, "The price in the summary is not as expected."),
                () -> assertEquals(expectedPaymentMethod, actualPaymentMethod, "The payment method in the summary is not as expected."),
                () -> assertEquals(expectedProductQuantity, actualQuantity, "The product quantity in the summary is not as expected.")
        );
    }

    @Test
    public void checkoutFormValidationTest() {

        selectProductAndGoToPaymentPage();
        fillOutRegistrationData("", "", "", "", "", "", "");
        submitPaymentDetails();
        acceptTerms();
        placeOrder();

        String errorMessage = driver.findElement(errorList).getText();

        String nameErrorMessage = "Imię płatnika jest wymaganym polem.";
        String lastNameErrorMessage = "Nazwisko płatnika jest wymaganym polem.";
        String streetErrorMessage = "Ulica płatnika jest wymaganym polem.";
        String zipCodeErrorMessage = "Kod pocztowy płatnika nie jest prawidłowym kodem pocztowym.";
        String cityErrorMessage = "Miasto płatnika jest wymaganym polem.";
        String phoneNumberErrorMessage = "Telefon płatnika jest wymaganym polem.";
        String emailErrorMEssage = "Adres email płatnika jest wymaganym polem.";

        assertAll(
                () -> assertTrue(errorMessage.contains(nameErrorMessage), "Displayed validation error message for the first name field was not as expected."),
                () -> assertTrue(errorMessage.contains(lastNameErrorMessage), "Displayed validation error message for the last name field was not as expected."),
                () -> assertTrue(errorMessage.contains(streetErrorMessage), "Displayed validation error message for the street field was not as expected."),
                () -> assertTrue(errorMessage.contains(zipCodeErrorMessage), "Displayed validation error message for the zip code field was not as expected."),
                () -> assertTrue(errorMessage.contains(cityErrorMessage), "Displayed validation error message for the city field was not as expected."),
                () -> assertTrue(errorMessage.contains(phoneNumberErrorMessage), "Displayed validation error message for the phone number field was not as expected."),
                () -> assertTrue(errorMessage.contains(emailErrorMEssage), "Displayed validation error message for the phone number field was not as expected.")
        );
    }

    @Test
    public void incorrectPhoneNumberTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        driver.findElement(mobileNumberField).clear();
        enterMobileNumber("abcdefghijk");
        submitPaymentDetails();
        acceptTerms();
        placeOrder();

        String expectedPhoneNumberErrorMessage = "Telefon płatnika nie jest poprawnym numerem telefonu.";
        String actualPhoneNumberErrorMessage = driver.findElement(errorList).getText();

        assertTrue(actualPhoneNumberErrorMessage.contains(expectedPhoneNumberErrorMessage), "Displayed wrong format error message for the phone number field was not as expected.");
    }

    private void enterEmail(String email) {

        driver.findElement(By.cssSelector("input#billing_email")).sendKeys(email);

    }

    private void buyOneProductAndSignUp() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        tickCreateAccount();
        setPassword("p4ssw0rdTesEl157+_@");
        submitPaymentDetails();
        acceptTerms();
        placeOrder();
        goToMyAccount();
        goToMyOrders();

    }


    private void buyOneProductNoSignUp() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails();
        acceptTerms();
        placeOrder();

    }

    private void selectProductAndGoToPaymentPage() {

        goToClimbingProductPage();
        addToCart();
        goToCart();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();

    }

    private void submitPaymentDetails() {

        driver.switchTo().frame(0);

        wait.until(ExpectedConditions.elementToBeClickable(cardNumberInput)).sendKeys("4242424242424242");

        driver.switchTo().defaultContent();
        driver.switchTo().frame(1);
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateField)).sendKeys("02/23");

        driver.switchTo().defaultContent();
        driver.switchTo().frame(2);
        wait.until(ExpectedConditions.elementToBeClickable(cardCVCField)).sendKeys("311");
    }

    private void acceptTerms() {

        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector("input#terms")).click();

    }

    private void fillOutRegistrationData(String firstName, String lastName, String email, String street, String postCode, String city, String mobileNumber) {

        enterName(firstName);
        enterLastname(lastName);

        WebElement countrySelectionDropdown = driver.findElement(By.id("billing_country"));
        Select country = new Select(countrySelectionDropdown);
        country.selectByValue("PL");

        enterEmail(email);
        enterStreet(street);
        enterPostcode(postCode);
        enterCity(city);
        enterMobileNumber(mobileNumber);

    }

    private void fillOutValidData() {

        fillOutRegistrationData("Egill", "Skallagrimsson", "e.skallagrimsson@postur.is",
                "Kveldulfsvegur", "11-123", "Mosfell", "600123456");

    }

    private void enterName(String firstName) {

        driver.findElement(nameField).sendKeys(firstName);

    }

    private void enterLastname(String lastName) {

        driver.findElement(lastNameField).sendKeys(lastName);

    }

    private void enterStreet(String street) {

        driver.findElement(billingAddressField).sendKeys(street);

    }

    private void enterPostcode(String postCode) {

        driver.findElement(postcodeField).sendKeys(postCode);

    }

    private void enterCity(String city) {

        driver.findElement(cityField).sendKeys(city);

    }

    private void enterMobileNumber(String mobileNumber) {

        driver.findElement(mobileNumberField).sendKeys(mobileNumber);

    }

    private void tickCreateAccount() {

        driver.findElement(By.cssSelector("input#createaccount")).click();

    }

    private void setPassword(String password) {

        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        driver.findElement(passwordField).sendKeys(password);

    }

    private void waitForProcessingEnd() {

        wait.until(ExpectedConditions.numberOfElementsToBe(blockedUI, 0));
    }

    private void placeOrder() {

        driver.findElement(placeOrderButton).submit();
        waitForProcessingEnd();

    }

    private void goToCart() {

        driver.findElement(viewCartButton).click();

    }

    private void goToMyOrders() {

        driver.findElement(myOrdersButton).click();

    }

    private void addToCart() {

        driver.findElement(addToCartButton).click();
    }

    private void goToClimbingProductPage() {


        driver.navigate().to("https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/");
    }

    private String getOrderStatus() {


        wait.until(ExpectedConditions.presenceOfElementLocated(orderStatusInfo));
        WebElement orderStatus = driver.findElement(orderStatusInfo);
        return orderStatus.getText();

    }

    private void goToMyAccount() {

        wait.until(ExpectedConditions.elementToBeClickable(myAccountButton)).click();

    }

    private void deleteMyAccount() {

        goToMyAccount();
        wait.until(ExpectedConditions.elementToBeClickable(deleteAccountButton)).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

    }

    private String getCurrentDate() {

        Calendar date = Calendar.getInstance();
        String month = getPolishMonth(date.get(Calendar.MONTH));
        String fullDate = date.get(Calendar.DAY_OF_MONTH) + " " + month + ", " + date.get(Calendar.YEAR);
        return fullDate;
    }

    private String getPolishMonth(int numberOfMonth) {
        String[] monthNames = {"stycznia",
                "lutego", "marca", "kwietnia", "maja", "czerwca", "lipca", "sierpnia", "września", "października",
                "listopada", "grudnia"};
        return monthNames[numberOfMonth];
    }
}