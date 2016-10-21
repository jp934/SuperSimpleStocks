package com.jpmorgan.dao;

import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jpmorgan.exceptions.StockException.Code.ADD_STOCK_TRADE_RECORD_FAILED;

@Component
@Profile(MemoryCacheManager.PROFILE_ENABLED)
public class MemoryCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(MemoryCacheManager.class);

    public static final String PROFILE_ENABLED = "cache.memory.profile.enabled";

    /**
     * Maintain the stock items in the hash map bucket
     */
    private Map<String,Stock> stocks = new HashMap<>();

    /**
     * Maintain the trade items in the hash map bucket
     */
    private List<Trade> trades = new ArrayList<>();

    /**
     * Get the list of stocks
     *
     * @return
     */
    @Override
    public Map<String, Stock> getAllStocks() {
        return this.stocks;
    }

    /**
     * Set the list of stocks
     */
    public void setStocks(Map<String, Stock> stocks) {
        this.stocks = stocks;
    }

    /**
     * Get the list of trades
     *
     * @return
     */
    @Override
    public List<Trade> getAllTrades() {
        return this.trades;
    }

    /**
     * Set the list of trades
     */
    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    @Override
    public Stock getStockBySymbol(String stockSymbol) throws StockException {
        Assert.notNull(stockSymbol, "Stock symbol should not be empty!");

        return getAllStocks().get(stockSymbol);
    }

    /**
     * Add the stock trade
     *
     * @param trade
     *
     * @return
     * @throws StockException
     */
    @Override
    public boolean recordTrade(Trade trade) throws StockException {
        Assert.notNull(trade, "Stock Trade should not be empty!");

        if (trade == null) {
            throw new StockException(ADD_STOCK_TRADE_RECORD_FAILED, trade.getStock().getStockSymbol());
        }

        return this.getAllTrades().add(trade);
    }
}
