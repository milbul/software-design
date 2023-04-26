package org.example.dao;

import org.example.model.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StockDao {
    private final Map<String, Stock> stockByName;

    public StockDao(Map<String, Stock> stockByName) {
        this.stockByName = stockByName;
    }

    public void create(Stock stock) {
        stockByName.put(stock.getName(), stock);
    }

    public Stock read(String name) {
        return stockByName.get(name);
    }

    public List<Stock> readAll() {
        return new ArrayList<>(stockByName.values());
    }

    public void update(Stock stock) {
        if (stockByName.containsKey(stock.getName())) {
            stockByName.put(stock.getName(), stock);
        }
    }

    public void delete(String name) {
        stockByName.remove(name);
    }
}
