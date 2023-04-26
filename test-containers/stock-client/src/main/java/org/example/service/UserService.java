package org.example.service;

import org.example.client.StockClient;
import org.example.dao.UserDao;
import org.example.model.User;
import org.example.model.Stock;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDao userDao;
    private final StockClient stockClient;
    private int userCount;

    public UserService(UserDao userDao, StockClient stockClient) {
        this.userDao = userDao;
        this.stockClient = stockClient;
        userCount = 0;
    }

    public int create() {
        int userCount = this.userCount;
        this.userCount++;
        userDao.create(new User(userCount));
        return userCount;
    }

    public User read(int userId) {
        return userDao.read(userId);
    }

    public void addAmount(int userId, int value) {
        User user = read(userId);
        user.addAmount(value);
    }

    public void addStock(int userId, Stock stock) {
        User user = read(userId);
        user.getStocks().put(stock.getName(), stock);
    }

    public int getTotalAmount(int userId) {
        User user = read(userId);
        int stockCost = user.getStocks().values().stream()
                .mapToInt(stock -> stockClient.getStockPrice(stock.getName()) * stock.getCount())
                .sum();
        return stockCost + user.getAmount();
    }

    public void buyStock(int userId, String stockName) {
        User user = read(userId);
        int stockPrice = stockClient.getStockPrice(stockName);
        if (user.getAmount() >= stockPrice) {
            if (stockClient.buyStock(stockName)) {
                Map<String, Stock> stockMap = user.getStocks();
                if (stockMap.containsKey(stockName)) {
                    int count = stockMap.get(stockName).getCount();
                    stockMap.put(stockName, new Stock(stockName, count + 1));
                } else {
                    stockMap.put(stockName, new Stock(stockName, 1));
                }
                user.addAmount(-stockPrice);
            }
        }
    }

    public void sellStock(int userId, String stockName) {
        User user = read(userId);
        int stockCount = user.getStocks().get(stockName).getCount();
        if (stockCount > 0 && stockClient.sellStock(stockName)) {
            int stockPrice = stockClient.getStockPrice(stockName);
            user.addAmount(stockPrice);
            user.getStocks().put(stockName, new Stock(stockName, stockCount - 1));
        }
    }

    public String getAll() {
        return userDao.readAll().stream().map(User::toString).collect(Collectors.joining("\n"));
    }
}
