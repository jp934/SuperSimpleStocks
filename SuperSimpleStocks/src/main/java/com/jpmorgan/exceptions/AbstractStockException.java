package com.jpmorgan.exceptions;

public abstract class AbstractStockException extends Exception implements IStockException {

    public AbstractStockException() {}

    public AbstractStockException(String code) {
        super(code);
    }

    public abstract int getStatusCode();
}
