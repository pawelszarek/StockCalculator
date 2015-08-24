package com.jpmorgan.controller;

import com.jpmorgan.exceptions.IncorrectStockDataException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.persistence.SimpleStockRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class StockCalculatorControllerTest {
    StockCalculatorController stockCalculatorController;

    @Before
    public void setUp() throws Exception {
        stockCalculatorController = new StockCalculatorController();
        stockCalculatorController.setStockRepository(new SimpleStockRepositoryImpl());
        Stock stock = generateStock("TEA", Stock.StockType.PREFERRED, 10, 10, 10, 100);
        Trade trade = generateTrade(new Date(), stock, 10, 100);
        stockCalculatorController.recordTrade(trade);
        stock = generateStock("TEA", Stock.StockType.COMMON, 100, 0, 100, 100);
        trade = generateTrade(new Date(), stock, 20, 100);
        stockCalculatorController.recordTrade(trade);

        //this trade is to test "too old" trade
        stock = generateStock("TEA", Stock.StockType.COMMON, 100, 0, 100, 100);
        trade = generateTrade(new Date(1000), stock, 20, 100);
        stockCalculatorController.recordTrade(trade);

        //this trade is to test different stock symbol
        stock = generateStock("POP", Stock.StockType.COMMON, 100, 0, 100, 100);
        trade = generateTrade(new Date(), stock, 20, 100);
        stockCalculatorController.recordTrade(trade);
    }


    @Test
    public void testCalculateDividentYield_common() throws Exception {
        Stock stock = generateStock("TEA", Stock.StockType.COMMON, 100, 0, 100, 100);
        assertEquals(stockCalculatorController.calculateDividentYield(stock), 1.0, 0);
    }

    @Test
    public void testCalculateDividentYield_preferred() throws Exception {
        Stock stock = generateStock("TEA", Stock.StockType.PREFERRED, 10, 10, 10, 100);
        assertEquals(stockCalculatorController.calculateDividentYield(stock), 1.0, 0);
    }

    @Test(expected = IncorrectStockDataException.class)
    public void testCalculateDividentYield_insufficientData() throws Exception {
        Stock stock = generateStock(null, Stock.StockType.PREFERRED, 10, 10, 10, 100);
        stockCalculatorController.calculateDividentYield(stock);
    }

    @Test
    public void testCalculatePERatio() throws Exception {
        Stock stock = generateStock("TEA", Stock.StockType.PREFERRED, 10, 10, 10, 100);
        assertEquals(stockCalculatorController.calculatePERatio(stock), 100.0, 0);
    }

    @Test
    public void testRecordTrade() throws Exception {
        Stock stock = generateStock("TEA", Stock.StockType.PREFERRED, 10, 10, 10, 100);
        Trade trade = generateTrade(new Date(), stock, 10, 100);
        assertTrue(stockCalculatorController.recordTrade(trade));
    }

    @Test
    public void testCalculateStockPrice() throws Exception {
        assertEquals(stockCalculatorController.calculateStockPrice("TEA"), 100.0, 0);
    }

    @Test
    public void testCalculateGBCEAllShareIndex() throws Exception {
        assertEquals(stockCalculatorController.calculateGBCEAllShareIndex(), 100.0, 0);
    }

    private Stock generateStock(String symbol, Stock.StockType stockType, float lastDivident, float fixedDivident, float parValue, float tickerPrice) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setStockType(stockType);
        stock.setLastDivident(lastDivident);
        stock.setFixedDivident(fixedDivident);
        stock.setParValue(parValue);
        stock.setTickerPrice(tickerPrice);
        return stock;
    }

    private Trade generateTrade(Date timestamp, Stock stock, int sharesQuantity, float price) {
        Trade trade = new Trade();
        trade.setTimestamp(timestamp);
        trade.setStock(stock);
        trade.setSharesQuantity(sharesQuantity);
        trade.setPrice(price);
        return trade;
    }
}