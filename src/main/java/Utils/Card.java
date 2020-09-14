package Utils;

import java.util.Properties;

public class Card {
    private final String number;
    private final String expirationDate;
    private final String cvc;

    public Card(Properties properties) {
        number = properties.getProperty("card.number");
        expirationDate = properties.getProperty("card.expirationDate");
        cvc = properties.getProperty("card.cvc");
    }

    public String getNumber() {
        return number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvc() {
        return cvc;
    }
}