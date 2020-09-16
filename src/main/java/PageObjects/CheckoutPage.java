package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    private WebDriverWait wait;

    @FindBy(css = "input[name='billing_first_name']")
    private WebElement firstNameField;

    @FindBy(css = "input[name='billing_last_name']")
    private WebElement lastNameField;

    @FindBy(css = "input#billing_phone")
    private WebElement mobileNumberField;

    @FindBy(css = ".select2-selection__arrow")
    private WebElement countryCodeArrow;

    @FindBy(css = "input#billing_city")
    private WebElement cityField;

    @FindBy(css = "input#billing_postcode")
    private WebElement postCodeField;

    @FindBy(css = "input#billing_address_1")
    private WebElement billingAddressField;

    @FindBy(css = "input#billing_email")
    private WebElement emailField;

    @FindBy(css = "input[name='cardnumber']")
    private WebElement cardNumberField;

    @FindBy(css = "input[name='cvc']")
    private WebElement cardCvcField;

    @FindBy(css = "input[name='exp-date']")
    private WebElement cardExpDateField;

    @FindBy(css = "input#terms")
    private WebElement acceptTermsCheckbox;

    @FindBy(css = "button#place_order")
    private WebElement placeOrderButton;

    @FindBy(css = "[name='__privateStripeFrame10']")
    private WebElement cardCvcFrame;

    @FindBy(css = "[name='__privateStripeFrame9']")
    private WebElement cardExpDateFrame;

    @FindBy(css = "[name='__privateStripeFrame8']")
    private WebElement cardNumberFrame;

    @FindBy(css = "input#createaccount")
    private WebElement createAccountCheckbox;

    @FindBy(css = "input#account_password")
    private WebElement setPasswordField;

    @FindBy(css = "a.showlogin")
    private WebElement signInButton;

    @FindBy(css = "input#username")
    private WebElement usernameField;

    @FindBy(css = "input#password")
    private WebElement passwordField;

    @FindBy(css = "button.woocommerce-form-login__submit")
    private WebElement logInButton;

    private String countryCodeCssSelector = "li[id*='-<country_code>']";
    private By loadingIconLocator = By.cssSelector(".blockOverlay");

    public CheckoutPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public CheckoutPage enterEmail(String email) {

        emailField.clear();
        emailField.sendKeys(email);

        return this;
    }

    public CheckoutPage fillOutRegistrationDetails(String firstName, String lastName, String email,
                                                   String street, String postCode, String city, String mobileNumber) {

        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        emailField.clear();
        emailField.sendKeys(email);

        WebElement countrySelectionDropdown = driver.findElement(By.id("billing_country"));
        Select country = new Select(countrySelectionDropdown);
        country.selectByValue("IS");

        billingAddressField.sendKeys(street);
        postCodeField.sendKeys(postCode);
        cityField.sendKeys(city);
        mobileNumberField.sendKeys(mobileNumber);

        return this;
    }

    public CheckoutPage submitPaymentDetails() {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardNumberFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardNumberField)).sendKeys("4242424242424242");

        driver.switchTo().defaultContent();
        driver.switchTo().frame(cardExpDateFrame);
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateField)).sendKeys("02/23");

        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardCvcFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardCvcField)).sendKeys("311");

        return this;
    }

    public CheckoutPage typeFirstName(String name) {

        wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(name);
        return this;
    }

    public CheckoutPage typeLastName(String lastName) {

        wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(lastName);
        return this;
    }

    public CheckoutPage chooseCountry(String countryCode) {

        wait.until(ExpectedConditions.elementToBeClickable(countryCodeArrow)).click();
        By countryCodeLocator = By.cssSelector(countryCodeCssSelector.replace("<country_code>", countryCode));
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeLocator)).click();
        return this;
    }

    public CheckoutPage typeAddress(String address) {

        wait.until(ExpectedConditions.elementToBeClickable(billingAddressField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(billingAddressField)).sendKeys(address);
        return this;
    }

    public CheckoutPage typePostalCode(String postalCode) {

        wait.until(ExpectedConditions.elementToBeClickable(postCodeField)).sendKeys(postalCode);
        return this;
    }

    public CheckoutPage typeCity(String city) {

        wait.until(ExpectedConditions.elementToBeClickable(cityField)).sendKeys(city);
        return this;
    }

    public CheckoutPage typePhone(String phone) {

        wait.until(ExpectedConditions.elementToBeClickable(mobileNumberField)).sendKeys(phone);
        return this;
    }

    public CheckoutPage typeEmail(String emailAddress) {

        wait.until(ExpectedConditions.elementToBeClickable(emailField)).sendKeys(emailAddress);
        return this;
    }

    public CheckoutPage typePassword(String password) {

        wait.until(ExpectedConditions.elementToBeClickable(setPasswordField)).sendKeys(password);
        return this;
    }

    public CheckoutPage typeCardNumber(String cardNumber) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardNumberFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardNumberField)).sendKeys(cardNumber);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardExpirationDate(String expirationDate) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardExpDateFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateField)).sendKeys(expirationDate);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCvcCode(String cvcCode) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardCvcFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardCvcField)).sendKeys(cvcCode);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage acceptTerms() {

        driver.switchTo().defaultContent();
        acceptTermsCheckbox.click();
        return this;
    }

    public OrderReceivedPage placeOrder() {

        placeOrderButton.submit();
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingIconLocator, 0));
        return new OrderReceivedPage(driver);
    }

    public CheckoutPage createNewAccount() {

        wait.until(ExpectedConditions.elementToBeClickable(createAccountCheckbox)).click();
        return this;
    }

    public CheckoutPage goToSignInSection() {

        signInButton.click();
        return this;
    }

    public CheckoutPage enterUserName(String userName) {

        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(userName);
        return this;
    }

    public CheckoutPage enterPassword(String password) {

        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        return this;
    }

    public CheckoutPage useLogInButton() {

        logInButton.click();
        return this;
    }
}