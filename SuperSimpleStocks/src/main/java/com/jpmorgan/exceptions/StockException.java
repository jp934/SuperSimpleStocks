package com.jpmorgan.exceptions;

import org.springframework.http.HttpStatus;

public class StockException extends AbstractStockException {

    /**
     * A set of different stock exceptions
     */
    public enum Code {
        STOCK_INVALID_TYPE("Invalid Stock type [%s]"),
        STOCK_TRADE_INVALID_TYPE("Invalid Stock Trade type [%s]"),
        STOCK_NOT_FOUND("Trading Stock not found"),
        STOCK_SYMBOL_NOT_SUPPORTED("Stock symbol [%s] not supported"),
        STOCK_TICKER_PRICE_GREATER_THAN_ZERO("Stock symbol [%s] ticker price should be greater than zero"),
        STOCK_QUANTITY_OF_SHARES_GREATER_THAN_ZERO("Stock [%s] quantity of shares in trading should be greater than zero"),
        STOCK_DIVIDEND_YIELD_GREATER_THAN_ZERO("Stock [%s] dividend yield should be greater than zero"),
        STOCK_TRADE_PRICE_GREATER_THAN_ZERO("Stock [%s] trade price should be greater than zero"),
        ADD_STOCK_TRADE_RECORD_FAILED("Something wrong, can't add the stock trade [%s] in the system");

        private String message;

        Code(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private Code code;

    public StockException(Code code, String... args) {
        super(String.format(code.getMessage(), (Object[]) args));
        this.code = code;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getCode() {
        return code == null ? IStockException.UNKNOWN_ERROR : code.name();
    }
}
