package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;
import org.apache.commons.lang3.StringUtils;

/**
 * Incrementally calculates the average values of the input {@link TradeItem}s.
 */
public class AverageTradeValueFilter implements TradeItemFilter {

    private Double averageSum;
    private int count;
    private String symbol;

    public AverageTradeValueFilter(String symbol) {
        this.symbol = symbol;
        this.averageSum = 0.0d;
        this.count = 0;
    }

    @Override
    public void calculate(TradeItem tradeItem) {
        if (tradeItem != null && StringUtils.equals(symbol, tradeItem.getSymbol())) {
            averageSum += tradeItem.getPrice();
            count++;
        }
    }

    @Override
    public double getValue() {
        return count == 0 ? 0 : averageSum / count;
    }
}
