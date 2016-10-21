package com.jpmorgan.dao;

import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Profile(DBCacheManager.PROFILE_ENABLED)
public class DBCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(DBCacheManager.class);

    public static final String PROFILE_ENABLED = "cache.database.profile.enabled";

    @Override
    public Map<String, Stock> getAllStocks() {

        log.debug("Get all the trading stocks");

        // TODO
        return null;
    }

    @Override
    public List<Trade> getAllTrades() {

        log.debug("Get all the trades");

        // TODO
        return null;
    }

    @Override
    public Stock getStockBySymbol(String stockSymbol) throws StockException {

        log.debug("Get all the stock symbols");

        // TODO
        return null;
    }

    @Override
    public boolean recordTrade(Trade trade) throws StockException {

        log.debug("Get all record trades");

        // TODO
        return false;
    }
}
