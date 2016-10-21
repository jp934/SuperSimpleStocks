package com.jpmorgan.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stock {

    private static final Logger log = LoggerFactory.getLogger(Stock.class);

    /**
     * A stock symbol
     */
    private String stockSymbol;

    /**
     * A stock type
     */
    private StockType stockType =  StockType.COMMON;

    /**
     * A stock last dividend
     */
    private double lastDividend;

    /**
     * A stock fixed dividend
     */
    private double fixedDividend;

    /**
     * A stock par value
     */
    private double parValue;

    /**
     * A stock ticker price
     */
    private double tickerPrice;

    public Stock(String stockSymbol, StockType stockType, double lastDividend, double fixedDividend, double parValue, double tickerPrice) {
        this.stockSymbol = stockSymbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.tickerPrice = tickerPrice;
    }

    /**
     * Get the stock symbol
     *
     * @return
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Set the stock symbol
     *
     * @param stockSymbol
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * Get the stock type
     *
     * @return
     */
    public StockType getStockType() {
        return stockType;
    }

    /**
     * Set the stock type
     *
     * @param stockType
     */
    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    /**
     * Get the stock last dividend
     *
     * @return
     */
    public double getLastDividend() {
        return lastDividend;
    }

    /**
     * Set the stock last dividend
     *
     * @param lastDividend
     */
    public void setLastDividend(double lastDividend) {
        this.lastDividend = lastDividend;
    }

    /**
     * Get the stock fixed dividend
     *
     * @return
     */
    public double getFixedDividend() {
        return fixedDividend;
    }

    /**
     * Set the stock fixed dividend
     *
     * @param fixedDividend
     */
    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    /**
     * Get the stock par value
     *
     * @return
     */
    public double getParValue() {
        return parValue;
    }

    /**
     * Set the stock par value
     *
     * @param parValue
     */
    public void setParValue(double parValue) {
        this.parValue = parValue;
    }

    /**
     * Get the stock ticker price
     *
     * @return
     */
    public double getTickerPrice() {
        return tickerPrice;
    }

    /**
     * Set the stock ticker price
     *
     * @param tickerPrice
     */
    public void setTickerPrice(double tickerPrice) {
        this.tickerPrice = tickerPrice;
    }

    /**
     * Get the dividend yield w.r.t stock type
     *
     * @return
     */
    public double getDividendYield() {
        double dividendYield = -1.0f;

        if (this.tickerPrice > 0.0f) {
            if (this.stockType == StockType.COMMON) {
                dividendYield = this.lastDividend / this.tickerPrice;
            } else {
                dividendYield = (this.fixedDividend * this.parValue) / this.tickerPrice;
            }
        }

        return dividendYield;
    }

    @Override
    public String toString() {
        return "Stock {" +
                "stockSymbol='" + this.getStockSymbol() +
                ", stockType=" + this.getStockType() +
                ", lastDividend=" + this.getLastDividend() +
                ", fixedDividend=" + this.getFixedDividend() +
                ", parValue=" + this.getParValue() +
                ", tickerPrice=" + this.getTickerPrice() +
                ", dividendYield=" + this.getDividendYield() +
                '}';
    }
}
