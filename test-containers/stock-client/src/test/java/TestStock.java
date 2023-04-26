import org.example.dao.UserDao;
import org.example.service.UserService;
import org.example.model.Stock;
import org.example.client.StockClient;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import java.net.URI;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@Testcontainers
public class TestStock {
    private static final String URI = "http://localhost:8080";

    @Container
    public static GenericContainer simpleWebServer
            = new FixedHostPortGenericContainer("stock-server:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    private final RestTemplate restTemplate = new RestTemplate();
    private final StockClient stockClient = new StockClient(URI, restTemplate);
    private final UserService userService = new UserService(new UserDao(new HashMap<>()), stockClient);

    @BeforeClass
    public static void mainSetUp() {
        simpleWebServer.start();
    }

    @Test
    public void buyStockTest() {
        addStock("stock_1", 100, 10);
        addStock("stock_2", 200, 20);
        int userId = userService.create();
        userService.addAmount(userId, 100);
        assertEquals(100, userService.read(userId).getAmount());

        userService.buyStock(userId, "stock_1");
        assertEquals(90, userService.read(userId).getAmount());
        assertEquals(1, userService.read(userId).getStocks().get("stock_1").getCount());

        userService.buyStock(userId, "stock_2");
        userService.buyStock(userId, "stock_2");
        assertEquals(50, userService.read(userId).getAmount());
        assertEquals(2, userService.read(userId).getStocks().get("stock_2").getCount());

        assertEquals(99, stockClient.getStockCount("stock_1"));
        assertEquals(198, stockClient.getStockCount("stock_2"));
        assertEquals(100, userService.getTotalAmount(userId));
    }

    @Test
    public void sellStockTest() {
        addStock("stock_1", 100, 10);
        addStock("stock_2", 200, 20);
        int userId = userService.create();
        userService.addStock(userId, new Stock("stock_1", 3));
        userService.addStock(userId, new Stock("stock_2", 5));
        assertEquals(0, userService.read(userId).getAmount());
        assertEquals(3, userService.read(userId).getStocks().get("stock_1").getCount());
        assertEquals(5, userService.read(userId).getStocks().get("stock_2").getCount());

        userService.sellStock(userId, "stock_1");
        assertEquals(10, userService.read(userId).getAmount());
        assertEquals(2, userService.read(userId).getStocks().get("stock_1").getCount());


        userService.sellStock(userId, "stock_2");
        userService.sellStock(userId, "stock_2");
        assertEquals(50, userService.read(userId).getAmount());
        assertEquals(3, userService.read(userId).getStocks().get("stock_2").getCount());

        assertEquals(101, stockClient.getStockCount("stock_1"));
        assertEquals(202, stockClient.getStockCount("stock_2"));
    }

    private void addStock(String name, int count, int price) {
        URI uri = UriComponentsBuilder.fromUriString(URI)
                .pathSegment("update-stock")
                .queryParam("name", name)
                .queryParam("price", price)
                .queryParam("count", count)
                .build()
                .toUri();

        restTemplate.getForObject(uri, Void.class);
    }
}
