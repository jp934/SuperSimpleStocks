package com.jpmorgan.services.stock;

import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Trade;

public interface StockService {

    /**
     * Calculate the stock dividend yield
     *
     * @param stockSymbol
     * @return
     *
     * @throws com.jpmorgan.exceptions.StockException
     */
    double calculateDividendYield(String stockSymbol) throws StockException;

    /**
     * Calculate the stock price earning ratio
     *
     * @param stockSymbol
     * @return
     *
     * @throws com.jpmorgan.exceptions.StockException
     */
    double calculatePriceEarningRatio(String stockSymbol) throws StockException;

    /**
     * Record a trade for the stock
     *
     * @param trade
     * @return
     *
     * @throws com.jpmorgan.exceptions.StockException
     */
    boolean recordTrade(Trade trade) throws StockException;

    /**
     * Calculate the GBCEIndex All share index
     *
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    double calculateGBCEIndexAllShareIndex() throws StockException;

    /**
     * Calculate the stock price for given symbol
     *
     * @param stockSymbol
     * @return
     *
     * @throws com.jpmorgan.exceptions.StockException
     */
    double calculateStockPrice(String stockSymbol) throws StockException;

    /**
     * Calculate the stock price for given symbol and time range
     *
     * @param stockSymbol
     * @param timeRange
     *
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    double calculateStockPriceWithTimeRange(String stockSymbol, int timeRange) throws StockException;
}
