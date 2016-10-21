package com.jpmorgan.services.stock;

import com.jpmorgan.dao.CacheManager;
import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static com.jpmorgan.exceptions.StockException.Code.*;

@Component
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    @Value("${stock.price.timerange:15}")
    private int timeRangeInMinutes;

    @Autowired
    private CacheManager cacheManager;

    /**
     * Calculate the stock dividend yield
     *
     * @param stockSymbol
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    @Override
    public double calculateDividendYield(String stockSymbol) throws StockException {

        Assert.notNull(stockSymbol, "Stock symbol should not be empty!");

        Stock stock = cacheManager.getStockBySymbol(stockSymbol);
        if (stock == null) {
            throw new StockException(STOCK_SYMBOL_NOT_SUPPORTED, stockSymbol);
        }

        if (stock.getTickerPrice() <= 0.0f) {
            throw new StockException(STOCK_TICKER_PRICE_GREATER_THAN_ZERO, stockSymbol);
        }

        double dividendYield = stock.getDividendYield();

        log.debug("Divided Yield calculated: {} for stock symbol: {}", dividendYield, stock.getStockSymbol());

        return dividendYield;
    }

    /**
     * Calculate the stock price earning ratio
     *
     * @param stockSymbol
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    @Override
    public double calculatePriceEarningRatio(String stockSymbol) throws StockException {

        Assert.notNull(stockSymbol, "Stock symbol should not be empty!");

        Stock stock = cacheManager.getStockBySymbol(stockSymbol);
        if (stock == null) {
            throw new StockException(STOCK_SYMBOL_NOT_SUPPORTED, stockSymbol);
        }

        double dividendYield = stock.getDividendYield();
        if (dividendYield <= 0.0f) {
            throw new StockException(STOCK_DIVIDEND_YIELD_GREATER_THAN_ZERO, stockSymbol);
        }

        // calculate the stock price earnings
        double priceEarningRatio = -1.0f;
        if (stock.getTickerPrice() > 0.0f) {
            priceEarningRatio = stock.getTickerPrice() / dividendYield;
        }

        log.debug("Price Earning Ratio: {} for stock symbol: {}", priceEarningRatio, stock.getStockSymbol());

        return priceEarningRatio;
    }

    /**
     * Record a trade for the stock
     *
     * @param trade
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    @Override
    public boolean recordTrade(Trade trade) throws StockException {

        Assert.notNull(trade, "Stock Trade should not be empty!");

        if (trade.getStock() == null) {
            throw new StockException(STOCK_NOT_FOUND);
        }

        if (trade.getQuantityOfShares() <= 0.0f) {
            throw new StockException(STOCK_QUANTITY_OF_SHARES_GREATER_THAN_ZERO, trade.getStock().getStockSymbol());
        }

        if (trade.getTradePrice() <= 0.0f) {
            throw new StockException(STOCK_TRADE_PRICE_GREATER_THAN_ZERO, trade.getStock().getStockSymbol());
        }

        // add or persist the stock trade record in the system
        boolean recordTrade = cacheManager.recordTrade(trade);
        if (recordTrade) {

            log.debug("Trading record: {} for stock symbol: {}", recordTrade, trade.getStock().getStockSymbol());
            trade.getStock().setTickerPrice(trade.getTradePrice());
        }

        return recordTrade;
    }

    /**
     * Calculate the GBCEIndex All share index
     *
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    @Override
    public double calculateGBCEIndexAllShareIndex() throws StockException {
        return calculateGBCEIndexAllShareIndex(timeRangeInMinutes);
    }

    /**
     * Calculate the GBCEIndex All share index
     *
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    public double calculateGBCEIndexAllShareIndex(int timeRangeInMinutes) throws StockException {

        double totalStockPrice = 1.0f;
        int numberOfStocks = 0;

        Map<String, Stock> stocks = cacheManager.getAllStocks();
        for (String stockSymbol : stocks.keySet()) {
            double stockPrice = calculateStockPriceWithTimeRange(stockSymbol, timeRangeInMinutes);
            if (stockPrice > 0.0f) {
                totalStockPrice *= stockPrice;
                numberOfStocks++;
            }
        }

        double allShareIndex = 0.0f;
        if (numberOfStocks > 0) {
            allShareIndex = Math.pow(totalStockPrice, (1 / numberOfStocks));
        }

        log.debug("GBCEIndex All Share index: {}", allShareIndex);

        return allShareIndex;
    }

    /**
     * Calculate the stock price for given symbol
     *
     * @param stockSymbol
     * @return
     *
     * @throws com.jpmorgan.exceptions.StockException
     */
    @Override
    public double calculateStockPrice(String stockSymbol) throws StockException {
        return calculateStockPriceWithTimeRange(stockSymbol, timeRangeInMinutes);
    }

    /**
     * Calculate the stock price for given symbol and time range
     *
     * @param stockSymbol
     * @param timeRange
     *
     * @return
     * @throws com.jpmorgan.exceptions.StockException
     */
    @Override
    public double calculateStockPriceWithTimeRange(String stockSymbol, int timeRange) throws StockException {

        Assert.notNull(stockSymbol, "Stock Symbol should not be empty!");

        Stock stock = cacheManager.getStockBySymbol(stockSymbol);
        if (stock == null) {
            throw new StockException(STOCK_SYMBOL_NOT_SUPPORTED, stockSymbol);
        }

        Collection<Trade> filteredTrades = DateTimeUtil.getTradesWithTimeRange(cacheManager.getAllTrades(), stockSymbol, timeRange);

        log.debug("Filtered trades for stock symbol: {} with given time range: {} and {}", stockSymbol, timeRange, filteredTrades.size());

        double stockPrice = 0.0f;
        double totalSharedQuantity = 0.0f;
        double totalTradePrice = 0.0f;

        for (Iterator<Trade> iterator = filteredTrades.iterator(); iterator.hasNext();) {

            Trade trade = iterator.next();
            if (trade != null) {

                // calculate the sum of Trade Price *  shares Quantity
                totalTradePrice += trade.getTradePrice() * trade.getQuantityOfShares();

                // calculate the total shares quantity
                totalSharedQuantity += trade.getQuantityOfShares();
            }
        }

        // calculate the stock price of Total Trade Price / Total Shared Quantity
        if (totalSharedQuantity > 0.0) {
            stockPrice = totalTradePrice / totalSharedQuantity;
        }

        return stockPrice;
    }
}
