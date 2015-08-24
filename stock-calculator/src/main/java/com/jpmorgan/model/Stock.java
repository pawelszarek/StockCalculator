package com.jpmorgan.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
public class Stock {
    public enum StockType {
        COMMON,
        PREFERRED
    }

    private String symbol;
    private StockType stockType;
    private double lastDivident;
    private double fixedDivident;
    private double parValue;
    private double tickerPrice;


    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public StockType getStockType() {
        return stockType;
    }
    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }
    public double getLastDivident() {
        return lastDivident;
    }
    public void setLastDivident(double lastDivident) {
        this.lastDivident = lastDivident;
    }
    public double getFixedDivident() {
        return fixedDivident;
    }
    public void setFixedDivident(double fixedDivident) {
        this.fixedDivident = fixedDivident;
    }
    public double getParValue() {
        return parValue;
    }
    public void setParValue(double parValue) {
        this.parValue = parValue;
    }
    public double getTickerPrice() {
        return tickerPrice;
    }
    public void setTickerPrice(double tickerPrice) {
        this.tickerPrice = tickerPrice;
    }
}
