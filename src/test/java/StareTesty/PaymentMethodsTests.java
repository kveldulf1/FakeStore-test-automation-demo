package StareTesty;

import Helpers.TestStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentMethodsTests {

    WebDriver driver;
    WebDriverWait wait;

    By addToCartButton = By.cssSelector("button[name='add-to-cart']");
    By blockedUI = By.cssSelector(".blockOverlay");
    By placeOrderButton = By.cssSelector("button#place_order");
    By cardNumberInput = By.cssSelector("input[name='cardnumber']");
    By nameField = By.cssSelector("input[name='billing_first_name']");
    By lastNameField = By.cssSelector("input[name='billing_last_name']");
    By mobileNumberField = By.cssSelector("input#billing_phone");
    By cityField = By.cssSelector("input#billing_city");
    By postcodeField = By.cssSelector("input#billing_postcode");
    By billingAddressField = By.cssSelector("input#billing_address_1");
    By myOrdersButton = By.cssSelector("li.woocommerce-MyAccount-navigation-link--orders>a");
    By viewCartButton = By.cssSelector("li.menu-item-200>a");
    By checkoutButton = By.cssSelector("a.checkout-button");
    By cardCVCField = By.cssSelector("input[name='cvc']");
    By cardExpDateField = By.cssSelector("input[name='exp-date']");
    By orderStatusInfo = By.cssSelector("p.woocommerce-notice");

    By secureAuthorizeFrame = By.xpath(".//iframe[contains(@src, 'authorize-with-url-inner')]");
    By authorizeFrame = By.cssSelector("iframe.AuthorizeWithUrlApp-content");
    By fullScreenFrame = By.cssSelector("iframe[name='acsFrame']");
    By authorizeButton = By.cssSelector("button#test-source-authorize-3ds");

    By secure2AuthFrame1 = By.cssSelector("iframe[name='__privateStripeFrame29'");
    By secure2AuthFrame2 = By.cssSelector("iframe[name='stripe-challenge-frame']");

    By stripeErrorMessageField = By.cssSelector("ul.wc-stripe-error>li");

    @RegisterExtension
    TestStatus status = new TestStatus();

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
    public void closeDriver(TestInfo info) throws IOException {
        if (status.isFailed) {
            System.out.println("Test screenshot is available at: " + takeScreenshot(info));
        }
        driver.quit();

    }

    @Test
    public void regularCardPaymentTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("378282246310005", "311", "02/22"); // credit card with no 3D Secure support.
        acceptTerms();
        placeOrder();
        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.",
                getOrderStatus(), "Failed to place the order with usage of credit card not supporting 3D Secure authentication system.");
    }

    @Test
    public void cardDeclined3DSecureTest() throws InterruptedException {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("4000008400001629", "311", "02/22");
        acceptTerms();
        placeOrder();

        switchToFrame(secureAuthorizeFrame);
        switchToFrame(authorizeFrame);
        switchToFrame(fullScreenFrame);

        wait.until(ExpectedConditions.presenceOfElementLocated(authorizeButton)).click();

        driver.switchTo().defaultContent();

        waitForProcessingEnd();

        String expectedCardDeclinedMessage = "Karta została odrzucona.";
        String actualCardDeclinedMessage = driver.findElement(stripeErrorMessageField).getText();

        assertEquals(expectedCardDeclinedMessage, actualCardDeclinedMessage, "The card has not been declined. Was the payment succesful?");
    }

    @Test
    public void succesful3DSecurePaymentTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("4000000000003220", "311", "02/22");
        acceptTerms();
        placeOrder();
        switchToFrame(secure2AuthFrame1);
        switchToFrame(secure2AuthFrame2);
        wait.until(ExpectedConditions.presenceOfElementLocated(authorizeButton)).click();
        driver.switchTo().defaultContent();
        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie 111111111111.",
                getOrderStatus(), "Failed to complete the payment process using the 3D Secure 2 authentication method.");


    }

    @Test
    public void failed3DSecurePaymentTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("4000000000003220", "311", "02/22");
        acceptTerms();
        placeOrder();
        switchToFrame(secure2AuthFrame1);
        switchToFrame(secure2AuthFrame2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#test-source-fail-3ds"))).click();
        driver.switchTo().defaultContent();

        String expectedFailedPaymentMessage = "Nie można przetworzyć tej płatności, spróbuj ponownie lub użyj alternatywnej metody. 11111111111";
        String actualFailedPaymentMessage = wait.until(ExpectedConditions.presenceOfElementLocated(stripeErrorMessageField)).getText();

        assertEquals(expectedFailedPaymentMessage, actualFailedPaymentMessage, "The payment has not been failed.");

    }

    @Test
    public void incompleteCVCValidationTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("378282246310005", "", "02/22");
        acceptTerms();
        placeOrder();
        driver.switchTo().defaultContent();
        assertEquals("Kod bezpieczeństwa karty jest niekompletny. 1111111111111111111", getStripeErrorMessage(), "The error message for empty CVC field was not as expected.");

    }

    @Test
    public void incompleteExpDateValidationTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("378282246310005", "311", "");
        acceptTerms();
        placeOrder();
        driver.switchTo().defaultContent();
        assertEquals("Data ważności karty jest niekompletna.", getStripeErrorMessage(), "The error message for empty expiry date field was not as expected");

    }

    @Test
    public void incompleteCardNumberValidationTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("4", "311", "11/22");
        acceptTerms();
        placeOrder();
        assertEquals("Numer karty jest niekompletny.", getStripeErrorMessage(), "The error message for empty card number field was not as expected");


    }

    @Test
    public void incorrectCardNumberValidationTest() {

        selectProductAndGoToPaymentPage();
        fillOutValidData();
        submitPaymentDetails("4000000000003229", "311", "11/22");
        acceptTerms();
        placeOrder();
        assertEquals("Numer karty nie jest prawidłowym numerem karty kredytowej.", getStripeErrorMessage(), "The error message for incorrect card number field was not as expected");

    }

    private String getStripeErrorMessage() {

        wait.until(d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        return driver.findElement(stripeErrorMessageField).getText();

    }

    private void switchToFrame(By frameLocator) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        wait.until(d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

    }

    private void enterEmail(String email) {

        driver.findElement(By.cssSelector("input#billing_email")).sendKeys(email);

    }

    private void selectProductAndGoToPaymentPage() {

        goToClimbingProductPage();
        addToCart();
        goToCart();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();

    }

    private void submitPaymentDetails(String cardNumber, String cvc, String expDate) {

        driver.switchTo().frame(0);

        wait.until(ExpectedConditions.elementToBeClickable(cardNumberInput)).sendKeys(cardNumber);

        driver.switchTo().defaultContent();
        driver.switchTo().frame(1);
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateField)).sendKeys(expDate);

        driver.switchTo().defaultContent();
        driver.switchTo().frame(2);
        wait.until(ExpectedConditions.elementToBeClickable(cardCVCField)).sendKeys(cvc);
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

        fillOutRegistrationData("Egill", "Skallagrimsson", "eee1g21311i12331121123311211212331312311112321233232431232322312313l@postur.is",
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

    private void waitForProcessingEnd() {

        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfElementsToBe(blockedUI, 0));
    }

    private void placeOrder() {

        driver.findElement(placeOrderButton).submit();

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

    private String takeScreenshot(TestInfo info) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\mkownacki1\\Documents\\Testelka\\screenshots\\" + info.getDisplayName() + " " + formatter.format(timeNow) + ".png";
        FileHandler.copy(screenshot, new File(path));
        return path;

    }
}


