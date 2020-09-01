package StareTesty;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class  CartTests {

    WebDriver driver;
    WebDriverWait wait;


    By addToCartButton = By.cssSelector("button[name='add-to-cart']");
    By productIslandPeakClimbing = By.cssSelector("a[href='?add-to-cart=42']");
    By viewCartButton = By.cssSelector("a[title='Zobacz koszyk']");
    By removeProductFromCartButton = By.cssSelector("a.remove");


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
    public void addOneProductToCartFromProductPageTest() {

        goToClimbingProductPage();
        addToCart();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[role='alert']")));
        Assertions.assertTrue(driver.findElements(removeProductFromCartButton).size() == 1,
                "Failed to add one product to cart from product page.");

    }

    @Test
    public void addOneProductFromCategoryPageTest() {

        goToClimbingCategory();
        driver.findElement(productIslandPeakClimbing).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton)).click();
        Assertions.assertEquals("1 Produkt", getProductsInCartAmount(), "Failed to add one product to cart from category page.");

    }

    @Test
    public void addTenIdenticalProductsToCartTest() {

        goToClimbingProductPage();
        setQuantityTo("10");
        addToCart();
        viewCart();
        Assertions.assertEquals("10", getQuantityValue(), "Attempt to add 10 products to cart was unsuccessful.");

    }

    @Test
    public void addTenDifferentProductsToCartTest() {

        goToWindSurfingCategory();
        AddAllProductsToCart();
        goToYogaAndPilatesCategory();
        AddAllProductsToCart();
        viewCart();
        List<WebElement> productsInCartList = driver.findElements(By.cssSelector("tr.woocommerce-cart-form__cart-item"));
        int getAddedProductsQuantinty = productsInCartList.size();
        Assertions.assertEquals(10, getAddedProductsQuantinty, "The quantity of products in cart is not as expected.");

    }

    @Test
    public void changeQuantityOfProductsAddedToCartTest() {

        goToClimbingProductPage();
        setQuantityTo("3");
        addToCart();
        viewCart();
        setQuantityTo("2");
        Assertions.assertEquals("2", getQuantityValue(), "Attempt to change the quantity of products added to cart was unsuccessful. Expected 2, but was" + getQuantityValue());
    }

    @Test
    public void removeProductFromCartTest() {

        goToClimbingProductPage();
        addToCart();
        viewCart();
        driver.findElement(removeProductFromCartButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.cart-empty")));
        String emptyCartAlert = driver.findElement(By.cssSelector("p.cart-empty")).getText();
        Assertions.assertEquals("Twój koszyk jest pusty.", emptyCartAlert, "Failed to remove the product from cart.");

    }

    private String getProductsInCartAmount() {

        return driver.findElement(By.cssSelector("span.count")).getText();
    }

    private void goToMyAccount() {

        driver.navigate().to("https://fakestore.testelka.pl/moje-konto/");
    }

    private void viewCart() {

        driver.navigate().to("https://fakestore.testelka.pl/koszyk/");
    }

    private void addToCart() {

        driver.findElement(addToCartButton).click();
    }

    private void goToClimbingProductPage() {

        driver.navigate().to("https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/");

    }

    private void goToClimbingCategory() {

        driver.navigate().to("https://fakestore.testelka.pl/product-category/wspinaczka/");

    }

    private void goToWindSurfingCategory() {

        driver.navigate().to("https://fakestore.testelka.pl/product-category/windsurfing/");
    }

    private void goToYogaAndPilatesCategory() {

        driver.navigate().to("https://fakestore.testelka.pl/product-category/yoga-i-pilates/");
    }

    private void AddAllProductsToCart() {                                        // for each

        List<WebElement> productsAddToCartButtons = driver.findElements(By.cssSelector("a.add_to_cart_button"));

        int i = 0;
        for (WebElement singleAddToCartButton : productsAddToCartButtons) {
            singleAddToCartButton.click();
            wait.until(ExpectedConditions.numberOfElementsToBe(viewCartButton, ++i));
        }


    }

    private void forAddAllProductsToCart() {

        List<WebElement> productsAddToCartButtons = driver.findElements(By.cssSelector("a.add_to_cart_button"));

        for (int i = 0; i < 5; i++) {
            productsAddToCartButtons.get(i).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(viewCartButton, i + 1));
        }
    }


    private void setQuantityTo(String quantity) {

        WebElement quantityInput = driver.findElement(By.cssSelector(""));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }

    private String getQuantityValue() {

        WebElement quantityInputAfterRefresh = driver.findElement(By.cssSelector("input.qty"));
        return quantityInputAfterRefresh.getAttribute("value");

    }

    private void deleteMyAccount() {

        goToMyAccount();
        driver.findElement(By.cssSelector("a.delete-me")).click();
        driver.switchTo().alert().accept();
        goToMyAccount();
        By logInHeader = By.cssSelector("div.u-column1.col-1>h2");
        String logInMessage = driver.findElement(logInHeader).getText();
        Assertions.assertEquals("Zaloguj się", logInMessage, "The account has not been deleted.");

    }
}
