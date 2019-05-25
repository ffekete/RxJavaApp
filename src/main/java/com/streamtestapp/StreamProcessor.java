package com.streamtestapp;

import com.streamtestapp.filter.TradeItemFilter;
import rx.Observable;

import java.util.List;

public class StreamProcessor {

    private List<TradeItemFilter> tradeItemFilters;

    public StreamProcessor(List<TradeItemFilter> tradeItemFilters) {
        this.tradeItemFilters = tradeItemFilters;
    }

    public void subscribe(Observable<TradeItem> observable) {
        observable.subscribe(tradeItem -> {
                    for (TradeItemFilter tradeItemFilter : tradeItemFilters) {
                        tradeItemFilter.calculate(tradeItem);
                    }
                }, Throwable::printStackTrace,
                () -> System.out.println("Done"));
    }
}
