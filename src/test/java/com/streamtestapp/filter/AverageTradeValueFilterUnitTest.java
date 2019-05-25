package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AverageTradeValueFilterUnitTest {

    private TradeItemFilter tradeItemFilterUnderTest;

    @Test
    public void testFilterShouldFindItem() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem2 = new TradeItem(1000L, "abc", 10d, 30, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem3 = new TradeItem(1000L, "def", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));

        tradeItemFilterUnderTest = new AverageTradeValueFilter("abc");

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        tradeItemFilterUnderTest.calculate(tradeItem2);
        tradeItemFilterUnderTest.calculate(tradeItem3);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(55d));
    }

    @Test
    public void testFilterShouldNotFindItem_noSymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem2 = new TradeItem(1000L, "abc", 10d, 30, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem3 = new TradeItem(1000L, "def", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));

        tradeItemFilterUnderTest = new AverageTradeValueFilter("ab");

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        tradeItemFilterUnderTest.calculate(tradeItem2);
        tradeItemFilterUnderTest.calculate(tradeItem3);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0.0d));
    }

    @Test
    public void testFilterShouldNotFindItem_nullSymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem2 = new TradeItem(1000L, "abc", 10d, 30, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem3 = new TradeItem(1000L, "def", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));

        tradeItemFilterUnderTest = new AverageTradeValueFilter(null);

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        tradeItemFilterUnderTest.calculate(tradeItem2);
        tradeItemFilterUnderTest.calculate(tradeItem3);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0.0d));
    }

    @Test
    public void testFilterShouldFindItem_bothSymbolsAreNull() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, null, 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem2 = new TradeItem(1000L, "abc", 10d, 30, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem3 = new TradeItem(1000L, "def", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));

        tradeItemFilterUnderTest = new AverageTradeValueFilter(null);

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        tradeItemFilterUnderTest.calculate(tradeItem2);
        tradeItemFilterUnderTest.calculate(tradeItem3);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(100.0d));
    }


    @Test
    public void testFilterShouldNotFindItem_nullSymbolInTradeItem() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, null, 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem2 = new TradeItem(1000L, "abc", 10d, 30, new HashSet<>(Arrays.asList('a', 'b')));
        TradeItem tradeItem3 = new TradeItem(1000L, "def", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));

        tradeItemFilterUnderTest = new AverageTradeValueFilter("ab");

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);
        tradeItemFilterUnderTest.calculate(tradeItem2);
        tradeItemFilterUnderTest.calculate(tradeItem3);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0.0d));
    }

    @Test
    public void testFilterShouldNotFindItem_nullItem() {
        // GIVEN
        tradeItemFilterUnderTest = new AverageTradeValueFilter("ab");

        // WHEN
        tradeItemFilterUnderTest.calculate(null);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0.0d));
    }

}