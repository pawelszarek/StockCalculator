package com.jpmorgan.model;

import java.util.Date;

public class Trade {
    public enum TradeIndicator {
        BUY,
        SELL
    }

    private Date timestamp;
    private Stock stock;
    private int sharesQuantity;
    private double price;
    private TradeIndicator tradeIndicator;

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Stock getStock() {
        return stock;
    }
    public void setStock(Stock stock) {
        this.stock = stock;
    }
    public int getSharesQuantity() {
        return sharesQuantity;
    }
    public void setSharesQuantity(int sharesQuantity) {
        this.sharesQuantity = sharesQuantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public TradeIndicator getTradeIndicator() {
        return tradeIndicator;
    }
    public void setTradeIndicator(TradeIndicator tradeIndicator) {
        this.tradeIndicator = tradeIndicator;
    }
}
