package ProjectFakeStore;

import PageObjects.CategoryPage;
import PageObjects.ProductPage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTest {

    @Test
    public void addOneProductToCartFromProductPageTest() {

        ProductPage productPage = new ProductPage(driver).goToClimbingProduct();
        productPage.demoNotice.close();
        int productAmount = productPage.addToCart().viewCart().getProductQuantity();
        Assertions.assertTrue(productAmount == 1,
                "Failed to add one product to cart from product page.");
    }

    @Test
    public void addOneProductFromCategoryPageTest() {

        CategoryPage categoryPage = new CategoryPage(driver).goToClimbingCategory();
        categoryPage.demoNotice.close();

        int productAmount = categoryPage.addToCartIslandProduct().viewCart().getProductQuantity();

        Assertions.assertTrue(productAmount == 1,
                "Failed to add one product to cart from category page.");
    }

    @Test
    public void addTenIdenticalProductsToCartTest() {

        ProductPage productPage = new ProductPage(driver).goToClimbingProduct();
        productPage.demoNotice.close();
        String productsAmount = productPage.setQuantityTo("10").addToCart().viewCart().getQuantityValue();

        assertEquals("10", productsAmount, "Attempt to add 10 products to cart was unsuccessful.");
    }

    @Test
    public void addTenDifferentProductsToCartTest() {

        CategoryPage categoryPage = new CategoryPage(driver).goToWindsurfingCategory();
        categoryPage.demoNotice.close();
        int numberOfProducts = categoryPage.addAllProductsToCart().header.viewCart().getNumberOfProducts();

        assertEquals(5, numberOfProducts, "The quantity of products in cart is not as expected.");
    }

    @Test
    public void changeQuantityOfProductsAddedToCartTest() {

        ProductPage productPage = new ProductPage(driver).goToClimbingProduct();
        productPage.demoNotice.close();

        int productsQuantity = productPage.addToCart().viewCart().setQuantityValue(10).updateCart().waitForProccesingEnd().getProductQuantity();
        assertTrue(productsQuantity == 10, "Actual products quantity in cart does not match expected quantity.");
    }

    @Test
    public void removeProductFromCartTest() {

        ProductPage productPage = new ProductPage(driver).goToClimbingProduct();
        productPage.demoNotice.close();

        boolean isCartEmpty = productPage.addToCart().viewCart().removeProduct().isCartEmpty();

        assertTrue(isCartEmpty == true, "The attempt to remove the product from the cart was unsuccessful.");
    }

}


