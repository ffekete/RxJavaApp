package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;
import org.apache.commons.lang3.StringUtils;


/**
 * Incrementally looks for the maximum item of the input {@link TradeItem}s.
 */
public class MaxTradeValueFilter implements TradeItemFilter {

    private Double maxValue;
    private String symbol;

    public MaxTradeValueFilter(String symbol) {
        this.symbol = symbol;
        this.maxValue = 0.0d;
    }

    @Override
    public void calculate(TradeItem tradeItem) {
        if (tradeItem != null && StringUtils.equals(symbol, tradeItem.getSymbol())) {
            if (tradeItem.getPrice() * tradeItem.getSize() > maxValue) {
                maxValue = tradeItem.getPrice() * tradeItem.getSize();
            }
        }
    }

    @Override
    public double getValue() {
        return maxValue;
    }
}
