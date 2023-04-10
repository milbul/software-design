import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        MongoDatabase db = MongoClients.create("mongodb://localhost:27017").getDatabase("catalog");
        MongoDao mongo = new MongoDao(db.getCollection("users"), db.getCollection("products"));
        RxHttpController controller = new RxHttpController(mongo);
        HttpServer.newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = controller.getResponse(req);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}