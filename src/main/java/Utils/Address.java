package Utils;

import java.util.Properties;

public class Address {
    private final String countryCode;
    private final String street;
    private final String postalCode;
    private final String city;

    public Address(Properties properties) {
        countryCode = properties.getProperty("address.countryCode");
        street = properties.getProperty("address.street");
        postalCode = properties.getProperty("address.postalCode");
        city = properties.getProperty("address.city");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}