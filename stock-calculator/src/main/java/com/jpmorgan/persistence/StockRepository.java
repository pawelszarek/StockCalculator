package com.jpmorgan.persistence;

import com.jpmorgan.model.Trade;

import java.util.List;

public interface StockRepository {

    public void recordTrade(Trade trade);
    public List<Trade> getRecentTrades(int minutes, String stockSymbol);
    public List<String> getStockSymbols();
}
