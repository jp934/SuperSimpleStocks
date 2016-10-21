package com.jpmorgan.model;

import java.util.Date;

public class Trade {

    private Date timestamp;

    private int quantityOfShares;

    private double tradePrice;

    private TradeType tradeType;

    private Stock stock;

    public Trade(Date timestamp, int quantityOfShares, double tradePrice, TradeType tradeType, Stock stock) {
        this.timestamp = timestamp;
        this.quantityOfShares = quantityOfShares;
        this.tradePrice = tradePrice;
        this.tradeType = tradeType;
        this.stock = stock;
    }

    /**
     * Get the stock trade date and time
     *
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Set the stock trade date and time
     *
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get the trade quantity of share
     *
     * @return
     */
    public int getQuantityOfShares() {
        return quantityOfShares;
    }


    /**
     * Set the trade quantity of share
     *
     * @param quantityOfShares
     */
    public void setQuantityOfShares(int quantityOfShares) {
        this.quantityOfShares = quantityOfShares;
    }

    /**
     * Set the trade price
     *
     * @return
     */
    public double getTradePrice() {
        return tradePrice;
    }


    /**
     * Get the trade price
     *
     * @param tradePrice
     */
    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    /**
     * Get the trade type
     *
     * @return
     */
    public TradeType getTradeType() {
        return tradeType;
    }

    /**
     * Set the trade type
     *
     * @param tradeType
     */
    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Get the stock for trading
     *
     * @return
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Set the stock for tradding
     *
     * @param stock
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Trade {" +
                "timestamp=" + timestamp +
                ", quantityOfShares=" + quantityOfShares +
                ", tradePrice=" + tradePrice +
                ", tradeType=" + tradeType +
                ", stock=" + stock +
                '}';
    }
}
