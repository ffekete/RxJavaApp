package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MaxTradeValueFilterUnitTest {

    private TradeItemFilter tradeItemFilterUnderTest;

    @Test
    public void testShouldFindMaxItem() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new MaxTradeValueFilter("abc");
        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(2000d));
    }

    @Test
    public void testShouldNotFindMaxItem_emptySymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new MaxTradeValueFilter("");
        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testShouldNotFindMaxItem_nullSymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new MaxTradeValueFilter(null);
        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testShouldNotFindMaxItem_emptySymbolIntradeitem() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, null, 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new MaxTradeValueFilter("q");
        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testShouldFindMaxItem_emptySymbolInBoth() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new MaxTradeValueFilter("");
        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(2000d));
    }

    @Test
    public void testShouldNotFindMaxItem_nullTradeItem() {
        // GIVEN
        tradeItemFilterUnderTest = new MaxTradeValueFilter("abc");
        // WHEN
        tradeItemFilterUnderTest.calculate(null);
        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testShouldFindMaxItem_moreItems() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "a", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem2 = new TradeItem(1001L, "b", 10d, 10, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem3 = new TradeItem(1002L, "b", 1001d, 10, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new MaxTradeValueFilter("b");
        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
        tradeItemFilterUnderTest.calculate(tradeItem2);
        assertThat(tradeItemFilterUnderTest.getValue(), is(100d));
        tradeItemFilterUnderTest.calculate(tradeItem3);
        assertThat(tradeItemFilterUnderTest.getValue(), is(10010d));
    }

}