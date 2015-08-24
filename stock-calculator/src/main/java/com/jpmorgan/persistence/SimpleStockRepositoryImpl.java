package com.jpmorgan.persistence;

import com.jpmorgan.model.Trade;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.jpmorgan.model.TradePredicates.*;

@Component
public class SimpleStockRepositoryImpl implements StockRepository {
    private Logger logger = Logger.getLogger(SimpleStockRepositoryImpl.class);
    private List<Trade> trades = new CopyOnWriteArrayList<>();

    @Override
    public void recordTrade(Trade trade) {
        logger.info("recordTrade");
        trades.add(trade);
    }

    @Override
    public List<Trade> getRecentTrades(int minutes, String stockSymbol) {
        logger.info("getRecentTrades");
        return filterEmployees(filterEmployees(trades, isTradeYoungerThan(minutes)), isTradeOfSpecificStockSymbol(stockSymbol));
    }

    @Override
    public List<String> getStockSymbols() {
        logger.info("getStockSymbols");
        Set<String> stockSymbols = trades.stream().map(trade -> trade.getStock().getSymbol()).collect(Collectors.toSet());
        return new ArrayList<>(stockSymbols);
    }



}
