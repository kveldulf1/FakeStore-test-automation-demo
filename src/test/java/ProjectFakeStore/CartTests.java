package ProjectFakeStore;

import Helpers.BaseTest;
import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTest {

    @Test
    public void addOneProductToCartFromProductPageTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();
        boolean isProductInCart = productPage
                .addToCart()
                .viewCart()
                .isProductInCart(testData.getProduct().getId());

        assertTrue(isProductInCart, "Remove button was not found for a product with id = "
                + testData.getProduct().getId() + ". " + "Was the product added to cart?");
    }

    @Test
    public void addOneProductFromCategoryPageTest() {

        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getCategoryURL());
        categoryPage.demoNotice.close();

        int productAmount = categoryPage
                .addToCart()
                .viewCart()
                .getProductQuantityField();

        Assertions.assertTrue(productAmount == 1,
                "Failed to add one product to cart from category page.");
    }

    @Test
    public void addTenIdenticalProductsToCartTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();
        int productsAmount = productPage
                .setQuantity(10)
                .addToCart()
                .viewCart()
                .getProductQuantityField();

        assertEquals(10, productsAmount, "Attempt to add 10 products to cart was unsuccessful.");
    }

    @Test
    public void addFiveDifferentProductsToCartTest() {

        CategoryPage categoryPage = new CategoryPage(driver).goTo(configuration.getBaseUrl() + testData.getCategoryURL());
        categoryPage.demoNotice.close();
        int numberOfProducts = categoryPage
                .addAllProductsToCart()
                .header
                .viewCart()
                .getNumberOfProducts();

        assertEquals(5, numberOfProducts, "The quantity of products in cart is not as expected.");
    }

    @Test
    public void changeQuantityOfProductsAddedToCartTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();

        int productsQuantity = productPage
                .addToCart()
                .viewCart()
                .setQuantity(10)
                .updateCart()
                .waitForProcessingEnd()
                .getProductQuantityField();

        assertTrue(productsQuantity == 10, "Actual products quantity in cart does not match expected quantity.");
    }

    @Test
    public void removeProductFromCartTest() {

        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();

        boolean isCartEmpty = productPage
                .addToCart()
                .viewCart()
                .removeProduct()
                .isCartEmpty();

        assertTrue(isCartEmpty, "The attempt to remove the product from the cart was unsuccessful.");
    }
}


