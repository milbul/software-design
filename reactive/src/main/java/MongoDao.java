import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import rx.Observable;

public class MongoDao {
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public MongoDao(MongoCollection<Document> users, MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    public Observable<String> addUser(User user) {
        return users
                .find(Filters.eq("id", user.getId()))
                .toObservable()
                .isEmpty()
                .flatMap(notFound -> {
                    if (notFound) {
                        return users.insertOne(user.toDocument()).map(f -> user.getId() + " registered");
                    } else {
                        return Observable.just(user.getId() + " already exists");
                    }
                });
    }

    public Observable<String> addProduct(Product product) {
        return products
                .find(Filters.eq("id", product.getId()))
                .toObservable()
                .isEmpty()
                .flatMap(notFound -> {
                    if (notFound) {
                        return products.insertOne(product.toDocument()).map(f -> product.getId() + " registered");
                    } else {
                        return Observable.just(product.getId() + " already exists");
                    }
                });
    }


    public Observable<User> getAllUsers() {
        return users.find().toObservable().map(User::new);
    }

    public Observable<String> getUserProducts(int id) {
        return users
                .find(Filters.eq("id", id))
                .toObservable()
                .map(d -> Currency.valueOf(d.getString("currency")))
                .flatMap(currency -> products
                        .find()
                        .toObservable()
                        .map(d -> new Product(d).changeCurrency(currency).toString()));
    }
}
