package Utils;

import java.util.Properties;

public class Contact {
    private final String phone;
    private final String email;

    public Contact(Properties properties) {

        phone = properties.getProperty("contact.phone");
        email = properties.getProperty("contact.emailAddress");
    }

    public String getPhone() {

        return phone;
    }

    public String getEmail() {

        return email;
    }
}