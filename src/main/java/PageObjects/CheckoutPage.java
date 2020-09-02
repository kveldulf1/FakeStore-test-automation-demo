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

    public CheckoutPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 5);

    }

    public CheckoutPage fillOutValidDetails() {

        fillOutRegistrationDetails("Egill", "Skallagrimsson", "thorolf@postur.is", "Kveldulfsvegur", "11-123", "Mosfell", "600321666");

        return this;
    }

    public CheckoutPage fillOutRegistrationDetails(String firstName, String lastName, String email, String street, String postCode, String city, String mobileNumber) {

        driver.findElement(nameFieldLocator).sendKeys(firstName);
        driver.findElement(lastNameFieldLocator).sendKeys(lastName);
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

        driver.switchTo().frame(0);

        wait.until(ExpectedConditions.elementToBeClickable(cardNumberInputLocator)).sendKeys("4242424242424242");

        driver.switchTo().defaultContent();
        driver.switchTo().frame(1);
        wait.until(ExpectedConditions.elementToBeClickable(cardExpDateFieldLocator)).sendKeys("02/23");

        driver.switchTo().defaultContent();
        driver.switchTo().frame(2);
        wait.until(ExpectedConditions.elementToBeClickable(cardCVCFieldLocator)).sendKeys("311");

        return this;
    }

    public CheckoutPage acceptTerms() {

        driver.switchTo().defaultContent();
        driver.findElement(acceptTermsLocator).click();

        return this;
    }

    public OrderReceivedPage placeOrder() {

        driver.findElement(placeOrderButtonLocator).submit();
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingIconLocator, 0));

        return new OrderReceivedPage(driver);

    }
}