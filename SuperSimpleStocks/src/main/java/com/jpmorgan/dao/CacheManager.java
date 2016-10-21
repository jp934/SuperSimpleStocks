package com.jpmorgan.dao;

import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

public interface CacheManager {

    /**
     * Get all the stocks
     *
     * @return
     */
    public Map<String, Stock> getAllStocks();

    /**
     * Get all the trades
     *
     * @return
     */
    public List<Trade> getAllTrades();

    /**
     * Get the stock by given symbol
     *
     * @param stockSymbol
     *
     * @return
     * @throws SocketException
     */
    public Stock getStockBySymbol(String stockSymbol) throws StockException;

    /**
     * Check the stock price record on given trade
     *
     * @param trade
     *
     * @return
     * @throws SocketException
     */
    public boolean recordTrade(Trade trade) throws StockException;
}
