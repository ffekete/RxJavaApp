package com.streamtestapp;

import java.util.Set;

public class TradeItem {
    private final long timeStamp;
    private final String symbol;
    private final double price;
    private final int size;
    private Set<Character> flags;

    public TradeItem(long timeStamp, String symbol, double price, int size, Set<Character> flags) {
        this.timeStamp = timeStamp;
        this.symbol = symbol;
        this.price = price;
        this.size = size;
        this.flags = flags;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getSize() {
        return size;
    }

    public Set<Character> getFlags() {
        return flags;
    }
}
