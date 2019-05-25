package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Incrementally looks for the {@link TradeItem}s containing the given character.
 */
public class TradeItemContainsFlagFilter implements TradeItemFilter {

    private List<TradeItem> tradeItems;
    private String symbol;
    private Character character;

    public TradeItemContainsFlagFilter(String symbol, Character character) {
        this.symbol = symbol;
        this.character = character;
        this.tradeItems = new ArrayList<>();
    }

    @Override
    public void calculate(TradeItem tradeItem) {
        if (tradeItem != null && StringUtils.equals(symbol, tradeItem.getSymbol())) {
            if (!CollectionUtils.isEmpty(tradeItem.getFlags()) && tradeItem.getFlags().contains(character)) {
                tradeItems.add(tradeItem);
            }
        }
    }

    @Override
    public double getValue() {
        return tradeItems.size();
    }
}
