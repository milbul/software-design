import rx.Observable;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import java.util.List;
import java.util.Map;

public class RxHttpController {
    private final MongoDao mongo;

    public RxHttpController(MongoDao mongo) {
        this.mongo = mongo;
    }

    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        Map<String, List<String>> params = request.getQueryParameters();
        return switch (path) {
            case ("add_user") -> addUser(params);
            case ("add_product") -> addProduct(params);
            case ("get_users") -> getUsers();
            case ("get_user_products") -> getUserProducts(params);
            default -> Observable.just("Invalid command: " + path);
        };
    }

    private Observable<String> addUser(Map<String, List<String>> params) {
        int id = Integer.parseInt(params.get("id").get(0));
        String name = params.get("name").get(0);
        Currency currency = Currency.valueOf(params.get("currency").get(0));
        return mongo.addUser(new User(id, name, currency)).map(Object::toString);
    }

    private Observable<String> addProduct(Map<String, List<String>> params) {
        int id = Integer.parseInt(params.get("id").get(0));
        String name = params.get("name").get(0);
        Currency currency = Currency.valueOf(params.get("currency").get(0));
        double price = Double.parseDouble(params.get("price").get(0));
        return mongo.addProduct(new Product(id, name, currency, price)).map(Object::toString);
    }

    private Observable<String> getUsers() {
        return mongo.getAllUsers().map(Object::toString);
    }

    private Observable<String> getUserProducts(Map<String, List<String>> params) {
        int id = Integer.parseInt(params.get("id").get(0));
        return mongo.getUserProducts(id);
    }
}
