package com.jpmorgan.util;

import com.jpmorgan.model.Trade;
import jersey.repackaged.com.google.common.base.Predicate;
import org.springframework.util.StringUtils;

import java.util.*;

public class DateTimeUtil {

    /**
     *  Get all the trades with given time range and stock symbol
     *
     * @param tradeList
     * @param stockSymbol
     * @param timeRange
     *
     * @return
     */
    public static Collection<Trade> getTradesWithTimeRange(List<Trade> tradeList, final String stockSymbol, int timeRange) {

        final Calendar calendar = Calendar.getInstance();
        if (timeRange > 0) {
            calendar.add(Calendar.MINUTE, -timeRange);
        }

        Collection<Trade> resultSet = filter(tradeList, new Predicate<Trade>() {

            @Override
            public boolean apply(Trade trade) {

                // check the stock symbol not empty, then return trades for particular stocks
                if (!StringUtils.isEmpty(stockSymbol)) {
                    if (stockSymbol.equalsIgnoreCase(trade.getStock().getStockSymbol())) {
                        return calendar.getTime().compareTo(trade.getTimestamp()) <= 0;
                    }

                    return false;
                }

                // check all trades and regardless of stock symbol
                return calendar.getTime().compareTo(trade.getTimestamp()) <= 0;
            }
        });

        return resultSet;
    }

    /**
     * Get the timestamp for given minutes
     *
     * @param minutes
     */
    public static Date getTimestampInMinutes(int minutes) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public interface DateTimeRangePredicate<T> {
        boolean apply(T value);
    }

    public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : col) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
