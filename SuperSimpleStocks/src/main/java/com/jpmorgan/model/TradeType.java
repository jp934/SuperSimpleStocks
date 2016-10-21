package com.jpmorgan.model;

public enum TradeType {

    /**
     * Unknown trade type.
     */
    NONE,

    /**
     * A trade type that represents the BUY of shares for specific stock.
     */
    BUY,

    /**
     * A trade type that represents the SELL of shares for the specific stock.
     */
    SELL;

    private String propertyValue;

    static {
        for (TradeType type : values()) {
            type.propertyValue = type.name().toLowerCase();
        }
    }

    /**
     * Get the Trade type value
     *
     * @return
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * Get the Trade type
     *
     * @param tradeType
     * @return
     */
    public static TradeType valueFromProperty(String tradeType) {
        for (TradeType type : values()) {
            if (type.propertyValue.equals(tradeType)) {
                return type;
            }
        }

        return TradeType.NONE;
    }
}
