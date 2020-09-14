package Utils;

import java.util.Properties;

public class Customer {
    private final String name;
    private final String lastName;

    public Customer(Properties properties) {
        name = properties.getProperty("customer.name");
        lastName = properties.getProperty("customer.lastName");
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}