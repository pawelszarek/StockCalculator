package com.jpmorgan.controller;

import com.jpmorgan.exceptions.IncorrectStockDataException;
import com.jpmorgan.exceptions.IncorrectTradeDataException;
import com.jpmorgan.exceptions.UnsupportedStockTypeException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.persistence.StockRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StockCalculatorController {
    private Logger logger = Logger.getLogger(StockCalculatorController.class);
    private StockRepository stockRepository;
    private static final int STOCK_PRICE_PERIOD_IN_MINUTES = 15;

    @RequestMapping(value = "/calculateDividendYield", method = RequestMethod.POST)
    public double calculateDividentYield(@RequestBody Stock stock) {
        logger.info("calculateDividendYield");
        validateStock(stock);
        double dividentYield;
        switch (stock.getStockType()) {
            case COMMON:
                dividentYield = stock.getLastDivident() / stock.getTickerPrice();
                break;
            case PREFERRED:
                dividentYield = stock.getFixedDivident() * stock.getParValue() / stock.getTickerPrice();
                break;
            default:
                throw new UnsupportedStockTypeException(stock.getStockType() + " is not supported stock type");
        }
        logger.info("divident yield is: " + dividentYield);
        return dividentYield;
    }

    @RequestMapping("/calculatePERatio")
    public double calculatePERatio(@PathVariable Stock stock) {
        logger.info("calculatePERatio");
        double dividentYield = calculateDividentYield(stock);
        if (dividentYield == 0) {
            throw new IncorrectStockDataException("Divident Yield cannot be zero");
        }
        double result = stock.getTickerPrice() / dividentYield;
        logger.info("P/E ratio is: " + result);
        return result;
    }

    @RequestMapping("/recordTrade")
    public boolean recordTrade(@PathVariable Trade trade) {
        logger.info("recordTrade");
        validateTrade(trade);
        stockRepository.recordTrade(trade);
        logger.info("trade has been recorded");
        return true;
    }

    @RequestMapping("/calculateStockPrice")
    public double calculateStockPrice(@PathVariable String stockSymbol) {
        logger.info("calculateStockPrice");
        List<Trade> recentTrades = stockRepository.getRecentTrades(STOCK_PRICE_PERIOD_IN_MINUTES, stockSymbol);
        double tradePrices = 0;
        double volume = 0;

        for (Trade trade : recentTrades) {
            tradePrices += (trade.getSharesQuantity() * trade.getPrice());
            volume += trade.getSharesQuantity();
        }
        if (volume == 0) {
            throw new IncorrectStockDataException("Quantity of shares for this stock in last " + STOCK_PRICE_PERIOD_IN_MINUTES + "is 0");
        }
        double result = tradePrices / volume;
        logger.info("Stock price for " + stockSymbol + " is: " + result);
        return result;
    }

    @RequestMapping("/calculateGBCEAllShareIndex")
    public double calculateGBCEAllShareIndex() {
        logger.info("calculateGBCEAllShareIndex");
        List<String> stockSymbolList = stockRepository.getStockSymbols();
        List<Double> stockPriceList = stockSymbolList.stream().map(this::calculateStockPrice).collect(Collectors.toList());
        double product = 1;
        for (Double stockPrice : stockPriceList) {
            product *= stockPrice;
        }
        double result = Math.pow(product, 1.0 / stockPriceList.size());
        logger.info("GBCEA All Share Index is: " + result);
        return result;
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @Autowired
    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    private void validateStock(Stock stock) {
        if (stock == null) {
            logger.error("Stock cannot be null");
            throw new IncorrectStockDataException("Stock cannot be null");
        }
        if (stock.getSymbol() == null) {
            logger.error("Symbol cannot be null");
            throw new IncorrectStockDataException("Symbol cannot be null");
        }
        if (stock.getTickerPrice() == 0) {
            logger.error("Ticker price cannot be negative or zero");
            throw new IncorrectStockDataException("ticker price cannot be negative or zero");
        }
    }

    private void validateTrade(Trade trade) {
        if (trade == null) {
            logger.error("Trade cannot be null");
            throw new IncorrectTradeDataException("Trade cannot be null");
        }
        if (trade.getPrice() == 0) {
            logger.error("Trade price cannot be 0");
            throw new IncorrectTradeDataException("Trade price cannot be 0");
        }
        if (trade.getSharesQuantity() == 0) {
            logger.error("Shares quantity cannot be 0");
            throw new IncorrectTradeDataException("Shares quantity cannot be 0");
        }
        if (trade.getStock() == null) {
            logger.error("Stock in trade cannot be null");
            throw new IncorrectTradeDataException("Stock in trade cannot be null");
        }
        validateStock(trade.getStock());
    }
}
