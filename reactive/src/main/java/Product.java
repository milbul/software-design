import org.bson.Document;

public class Product {
    private final int id;
    private final String name;
    private final Currency currency;
    private final double price;

    public Product(int id, String name, Currency currency, double price) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

    public Product(Document document) {
        this(document.getInteger("id"),
                document.getString("name"),
                Currency.valueOf(document.getString("currency")),
                document.getDouble("price")
        );
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public Document toDocument() {
        return new Document().append("id", id).append("name", name).append("currency", currency.toString()).append("price", price);
    }

    public Product changeCurrency(Currency toCurrency) {
        return new Product(id, name, toCurrency, price * Currency.convertRatio(currency, toCurrency));
    }
}
