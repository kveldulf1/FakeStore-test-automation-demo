package Utils;

import java.util.Properties;

public class Password {

    private final String password;

    public Password (Properties properties){

        password = properties.getProperty("newPassword");
    }

    public String getPassword() {
        return password;
    }
}
