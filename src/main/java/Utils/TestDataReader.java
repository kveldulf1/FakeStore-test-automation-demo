package Utils;

public class TestDataReader extends FileReader {

    private String testDataLocation;

    private Product product;
    private String categoryURL;
    private String[] productPages;
    private Customer customer;
    private Address address;
    private Contact contact;
    private Card card;

    public TestDataReader(String testDataLocation) {

        super(testDataLocation);
        this.testDataLocation = testDataLocation;
    }

    void loadData() {

        product = new Product(properties);
        product = new Product(properties);
        customer = new Customer(properties);
        address = new Address(properties);
        contact = new Contact(properties);
        card = new Card(properties);
        categoryURL = properties.getProperty("category.url");
    }

    public String getTestDataLocation() {
        return testDataLocation;
    }

    public Product getProduct() {
        return product;
    }

    public String getCategoryURL() {
        return categoryURL;
    }

    public String[] getProductPages() {
        return productPages;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public Card getCard() {
        return card;
    }
}

