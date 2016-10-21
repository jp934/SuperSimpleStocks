package com.jpmorgan.exceptions;

public interface IStockException {

    static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

    String getMessage();

    String getCode();
}
