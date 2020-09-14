package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    private WebDriverWait wait;

    private By nameFieldLocator = By.cssSelector("input[name='billing_first_name']");
    private By lastNameFieldLocator = By.cssSelector("input[name='billing_last_name']");
    private By mobileNumberFieldLocator = By.cssSelector("input#billing_phone");
    private By countryCodeArrowLocator = By.cssSelector(".select2-selection__arrow");
    private String countryCodeCssSelector = "li[id*='-<country_code>']";
    private By cityFieldLocator = By.cssSelector("input#billing_city");
    private By postcodeFieldLocator = By.cssSelector("input#billing_postcode");
    private By billingAddressFieldLocator = By.cssSelector("input#billing_address_1");
    private By emailFieldLocator = By.cssSelector("input#billing_email");
    private By cardNumberInputLocator = By.cssSelector("input[name='cardnumber']");
    private By cardCVCFieldLocator = By.cssSelector("input[name='cvc']");
    private By cardExpDateFieldLocator = By.cssSelector("input[name='exp-date']");
    private By acceptTermsLocator = By.cssSelector("input#terms");
    private By placeOrderButtonLocator = By.cssSelector("button#place_order");
    private By loadingIconLocator = By.cssSelector(".blockOverlay");
    private By cvcFieldFrameLocator = By.cssSelector("[name='__privateStripeFrame10']");
    private By expirationDateFrameLocator = By.cssSelector("[name='__privateStripeFrame9']");
    private By cardNumberFrameLocator = By.cssSelector("[name='__privateStripeFrame8']");
    private By createAccountCheckboxLocator = By.cssSelector("input#createaccount");
    private By setPasswordFieldLocator = By.cssSelector("input#account_password");
    private By signInButtonLocator = By.cssSelector("a.showlogin");
    private By usernameFieldLocator = By.cssSelector("input#username");
    private By passwordFieldLocator = By.cssSelector("input#password");
    private By logInButttonLocator = By.cssSelector("button.woocommerce-form-login__submit");


    public CheckoutPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 5);

    }

    public CheckoutPage fillOutValidDetails() {

        fillOutRegistrationDetails("Egill", "Skallagrimsson", "thorolf@postur.is",
                "Kveldulfsvegur", "11-123", "Mosfell", "600321666");

        return this;
    }

    public CheckoutPage enterEmail(String email) {

        driver.findElement(emailFieldLocator).clear();
        driver.findElement(emailFieldLocator).sendKeys(email);

        return this;
    }

    public CheckoutPage fillOutRegistrationDetails(String firstName, String lastName, String email,
                                                   String street, String postCode, String city, String mobileNumber) {

        driver.findElement(nameFieldLocator).sendKeys(firstName);
        driver.findElement(lastNameFieldLocator).sendKeys(lastName);
        driver.findElement(emailFieldLocator).clear();
        driver.findElement(emailFieldLocator).sendKeys(email);

        WebElement countrySelectionDropdown = driver.findElement(By.id("billing_country"));
        Select country = new Select(countrySelectionDropdown);
        country.selectByValue("PL");

        driver.findElement(billingAddressFieldLocator).sendKeys(street);
        driver.findElement(postcodeFieldLocator).sendKeys(postCode);
        driver.findElement(cityFieldLocator).sendKeys(city);
        driver.findElement(mobileNumberFieldLocator).sendKeys(mobileNumber);

        return this;
    }

    public CheckoutPage submitPaymentDetails() {

        WebElement cardNumberInputFrame = driver.findElement(cardNumberFrameLocator);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardNumberInputFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardNumberInputLocator)).sendKeys("4242424242424242");

        driver.switchTo().defaultContent();

        WebElement expirationDateFrame = driver.findElement(expirationDateFrameLocator);
        driver.switchTo().frame(expirationDateFrame);
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateFieldLocator)).sendKeys("02/23");

        driver.switchTo().defaultContent();

        WebElement cvcFieldFrame = driver.findElement(cvcFieldFrameLocator);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cvcFieldFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardCVCFieldLocator)).sendKeys("311");

        return this;
    }

    public CheckoutPage typeName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(nameFieldLocator)).sendKeys(name);
        return this;
    }

    public CheckoutPage typeLastName(String lastName) {
        wait.until(ExpectedConditions.elementToBeClickable(lastNameFieldLocator)).sendKeys(lastName);
        return this;
    }

    public CheckoutPage chooseCountry(String countryCode) {
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeArrowLocator)).click();
        By countryCodeLocator = By.cssSelector(countryCodeCssSelector.replace("<country_code>", countryCode));
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeLocator)).click();
        return this;
    }

    public CheckoutPage typeAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(billingAddressFieldLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(billingAddressFieldLocator)).sendKeys(address);
        return this;
    }

    public CheckoutPage typePostalCode(String postalCode) {
        wait.until(ExpectedConditions.elementToBeClickable(postcodeFieldLocator)).sendKeys(postalCode);
        return this;
    }

    public CheckoutPage typeCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(cityFieldLocator)).sendKeys("Sopot");
        return this;
    }

    public CheckoutPage typePhone(String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(mobileNumberFieldLocator)).sendKeys(phone);
        return this;
    }

    public CheckoutPage typeEmail(String emailAddress) {
        wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator)).sendKeys(emailAddress);
        return this;
    }

    public CheckoutPage typeCardNumber(String cardNumber) {
        WebElement cardNumberInputFrame = driver.findElement(cardNumberFrameLocator);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardNumberInputFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardNumberInputLocator)).sendKeys(cardNumber);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardExpirationDate(String expirationDate) {

        WebElement expirationDateFrame = driver.findElement(expirationDateFrameLocator);
        driver.switchTo().frame(expirationDateFrame);
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateFieldLocator)).sendKeys(expirationDate);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCvcCode(String cvcCode) {
        WebElement cvcFieldFrame = driver.findElement(cvcFieldFrameLocator);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cvcFieldFrame));
        wait.until(ExpectedConditions.elementToBeClickable(cardCVCFieldLocator)).sendKeys(cvcCode);
        driver.switchTo().defaultContent();
        return this;
    }


    public CheckoutPage acceptTerms() {

        driver.switchTo().defaultContent();
        waitForProccesingEnd();
        driver.findElement(acceptTermsLocator).click();

        return this;
    }

    public OrderReceivedPage placeOrder() {

        driver.findElement(placeOrderButtonLocator).submit();
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingIconLocator, 0));

        return new OrderReceivedPage(driver);
    }

    public CheckoutPage waitForProccesingEnd() {

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingIconLocator, 0));

        return this;
    }

    public CheckoutPage createNewAccount() {

        wait.until(ExpectedConditions.elementToBeClickable(createAccountCheckboxLocator)).click();

        return this;
    }

    public CheckoutPage setPassword(String password) {

        wait.until(ExpectedConditions.elementToBeClickable(setPasswordFieldLocator)).sendKeys(password);

        return this;
    }

    public CheckoutPage goToSignInSection() {

        driver.findElement(signInButtonLocator).click();

        return this;
    }

    public CheckoutPage enterUserName(String userName) {

        wait.until(ExpectedConditions.elementToBeClickable(usernameFieldLocator)).sendKeys(userName);

        return this;
    }

    public CheckoutPage enterPassword(String email) {

        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldLocator)).sendKeys("Egil123!!!");

        return this;
    }

    public CheckoutPage useLogInButton() {

        driver.findElement(logInButttonLocator).click();

        return this;
    }
}
