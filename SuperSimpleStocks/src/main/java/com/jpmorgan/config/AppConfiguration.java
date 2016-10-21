package com.jpmorgan.config;

import com.jpmorgan.dao.CacheManager;
import com.jpmorgan.dao.MemoryCacheManager;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.StockType;
import com.jpmorgan.model.Trade;
import com.jpmorgan.model.TradeType;
import com.jpmorgan.services.stock.StockService;
import com.jpmorgan.services.stock.StockServiceImpl;
import com.jpmorgan.util.DateTimeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfiguration {

    @Bean(name = "teaStock")
    public Stock getStockTEA() {
        return new Stock("TEA", StockType.COMMON, 0.0f, 0.0f, 100, 25.9f);
    }

    @Bean(name = "popStock")
    public Stock getStockPOP() {
        return new Stock("POP", StockType.COMMON, 8.0f, 0.0f, 100, 15.9f);
    }

    @Bean(name = "aleStock")
    public Stock getStockALE() {
        return new Stock("ALE", StockType.COMMON, 23, 0.0f, 60, 32.9f);
    }

    @Bean(name = "ginStock")
    public Stock getStockGIN() {
        return new Stock("GIN", StockType.PREFERRED, 8, 0.02f, 100, 10.9f);
    }

    @Bean(name = "joeStock")
    public Stock getStockJOE() {
        return new Stock("JOE", StockType.COMMON, 13, 0.0f, 250, 99.9f);
    }

    @Bean(name = "stockService")
    public StockService getStockService() {
        return new StockServiceImpl();
    }

    @Bean(name = "memoryStockManager")
    public CacheManager memoryStockManager() {

        MemoryCacheManager cacheManager = new MemoryCacheManager();
        cacheManager.setStocks(getStocks());
        cacheManager.setTrades(getStockTrades());

        return cacheManager;
    }

    @Bean(name = "stocks")
    public Map<String, Stock> getStocks() {

        Map<String, Stock> stocks = new HashMap();

        stocks.put("TEA", getStockTEA());
        stocks.put("POP", getStockPOP());
        stocks.put("ALE", getStockALE());
        stocks.put("GIN", getStockGIN());
        stocks.put("JOE", getStockJOE());

        return stocks;
    }

    @Bean(name = "stockTrades")
    public List<Trade> getStockTrades() {

        List<Trade> stockTrades = new ArrayList<>();

        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-6), 12, 14.f, TradeType.BUY, getStockTEA()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-2), 34, 23.f, TradeType.SELL, getStockALE()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-5), 78, 45.f, TradeType.BUY, getStockJOE()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-9), 89, 67.f, TradeType.BUY, getStockJOE()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-23), 67, 89.f, TradeType.SELL, getStockTEA()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-12), 23, 24.f, TradeType.BUY, getStockTEA()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-10), 34, 45.f, TradeType.SELL, getStockGIN()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-14), 45, 67.f, TradeType.SELL, getStockPOP()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-42), 78, 82.f, TradeType.BUY, getStockPOP()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-38), 23, 29.f, TradeType.BUY, getStockGIN()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-8), 89, 39.f, TradeType.SELL, getStockTEA()));
        stockTrades.add(new Trade(DateTimeUtil.getTimestampInMinutes(-25), 28, 58.f, TradeType.BUY, getStockALE()));

        return stockTrades;
    }
}
