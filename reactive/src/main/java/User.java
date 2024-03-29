import org.bson.Document;

public class User {
    public final int id;
    public final String name;
    public final Currency currency;


    public User(Document doc) {
        this(doc.getDouble("id").intValue(), doc.getString("name"), Currency.valueOf(doc.getString("currency")));
    }

    public User(int id, String name, Currency currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Document toDocument() {
        return new Document().append("id", id).append("name", name).append("currency", currency.toString());
    }
}