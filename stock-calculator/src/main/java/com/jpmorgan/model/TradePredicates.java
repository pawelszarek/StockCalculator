package com.jpmorgan.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TradePredicates {

    public static Predicate<Trade> isTradeYoungerThan(final int minutes) {
        ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime minutesAgo = now.minusMinutes(minutes);
        return p -> p.getTimestamp().toInstant().isAfter(minutesAgo.toInstant());
    }

    public static Predicate<Trade> isTradeOfSpecificStockSymbol(final String stockSymbol) {
        return p -> stockSymbol.equals(p.getStock().getSymbol());
    }

    public static List<Trade> filterEmployees (List<Trade> trades, Predicate<Trade> predicate) {
        return trades.stream().filter( predicate ).collect(Collectors.<Trade>toList());
    }
}
