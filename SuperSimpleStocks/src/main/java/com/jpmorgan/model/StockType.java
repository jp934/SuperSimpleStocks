package com.jpmorgan.model;

public enum StockType {

    /**
     * Unknown stock type.
     */
    NONE,

    /**
     * A common stock and the dividend yield can be calculated using the last dividend.
     */
    COMMON,

    /**
     * A preferred stock and the dividend yield can be calculated using the fixed dividend.
     */
    PREFERRED;

    private String propertyValue;

    static {
        for (StockType type : values()) {
            type.propertyValue = type.name().toLowerCase();
        }
    }

    /**
     * Get the Stock type value
     *
     * @return
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * Get the Stock type
     *
     * @param stockType
     * @return
     */
    public static StockType valueFromProperty(String stockType) {
        for (StockType type : values()) {
            if (type.propertyValue.equals(stockType)) {
                return type;
            }
        }

        return StockType.NONE;
    }
}
