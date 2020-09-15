package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage extends BasePage {

    private WebDriverWait wait;

    @FindBy(css = "tr.cart_item")
    private List<WebElement> productsInCartField;

    @FindBy(css = "form>.shop_table")
    private WebElement shopTable;

    @FindBy(css = "form>.shop_table")
    private List<WebElement> shopTables;

    @FindBy(css = "div.quantity>input")
    private WebElement productQuantityField;

    @FindBy(css = "button[name='update_cart']")
    private WebElement updateCartButton;

    @FindBy(css = "button[name='update_cart']")
    private WebElement loadingIcon;

    @FindBy(css = "a.remove")
    private WebElement removeProductFromCartButton;

    @FindBy(css = "p.cart-empty")
    private WebElement emptyCartMessageContainerField;

    @FindBy(css = "a.checkout-button")
    private WebElement checkoutButton;

    private String removeProductButtonCssSelector = "a[data-product_id='<product_id>']";
    private By loadingIconLocator = By.cssSelector(".blockOverlay");


    public CartPage(WebDriver driver) {

        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public int getProductQuantityField() {

        String productsQuantity = wait.until(ExpectedConditions.visibilityOf(productQuantityField)).getAttribute("value");
        int quantity = Integer.parseInt(productsQuantity);

        return quantity;
    }

    public int getNumberOfProducts() {

        wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.visibilityOf(shopTable));
        return productsInCartField.size();
    }

    public CartPage setQuantity(int quantity) {

        productQuantityField.clear();
        productQuantityField.sendKeys(Integer.toString(quantity));
        return this;
    }

    public CartPage updateCart() {

        wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.elementToBeClickable(updateCartButton)).click();
        return this;
    }

    public CartPage waitForProcessingEnd() {

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBe(loadingIconLocator, 0));
        return this;
    }

    public CartPage removeProduct() {

        wait.until(ExpectedConditions.elementToBeClickable(removeProductFromCartButton)).click();
        wait.until(ExpectedConditions.visibilityOf(emptyCartMessageContainerField)).getText();
        return this;
    }

    public boolean isCartEmpty() {

        int shopTableElements = shopTables.size();
        if (shopTableElements == 1) {
            return false;
        } else if (shopTableElements == 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Wrong number of shop table elements: there can be only one or none.");
        }
    }

    public CheckoutPage goToCheckOut() {

        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();

        return new CheckoutPage(driver);
    }

    public boolean isProductInCart(String productId) {
        waitForShopTable();
        By removeProductLocator = By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId));
        int productRecords = driver.findElements(removeProductLocator).size();
        boolean presenceOfProduct = false;
        if (productRecords == 1) {
            presenceOfProduct = true;
        } else if (productRecords > 1) {
            throw new IllegalArgumentException("There is more than one record for the product in cart.");
        }
        return presenceOfProduct;
    }

    private void waitForShopTable() {

        WebDriverWait wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.visibilityOf(shopTable));
    }
}


