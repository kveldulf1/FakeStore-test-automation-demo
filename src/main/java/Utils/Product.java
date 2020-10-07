package Utils;

import java.util.Properties;

public class Product {
    private final String id;
    private final String url;

    public Product(Properties properties) {

        id = properties.getProperty("product.id");
        url = properties.getProperty("product.url");
    }

    public String getId() {

        return id;
    }

    public String getUrl() {

        return url; }
}