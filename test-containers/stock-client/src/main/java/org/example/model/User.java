package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final int userId;
    private final Map<String, Stock> stocks;

    private int amount;

    public User(int userId) {
        this.userId = userId;
        this.amount = 0;
        this.stocks = new HashMap<>();
    }

    public int getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public Map<String, Stock> getStocks() {
        return stocks;
    }

    public void addAmount(int value) {
        amount += value;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId + '\'' +
                ", stocks='" + stocks + '\'' +
                ", amount='" + amount +
                '}';
    }
}
