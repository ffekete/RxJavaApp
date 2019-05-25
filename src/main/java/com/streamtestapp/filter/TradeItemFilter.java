package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;

public interface TradeItemFilter {

    /**
     * Does a domain-specific calculation on a {@link TradeItem}.
     */
    void calculate(TradeItem tradeItem);


    /**
     * @return the calculated value calculated by function calculate().
     */
    double getValue();

}
