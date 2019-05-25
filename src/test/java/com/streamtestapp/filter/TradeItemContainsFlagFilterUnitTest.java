package com.streamtestapp.filter;

import com.streamtestapp.TradeItem;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TradeItemContainsFlagFilterUnitTest {

    private TradeItemFilter tradeItemFilterUnderTest;

    @Test
    public void testFilterShouldFindItem() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", 'a');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(1d));
    }

    @Test
    public void testFilterShouldNotFindItem_characterNotIcluded() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_nullCharacter() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", null);

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_nullSymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter(null, 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_emptySymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("", 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_nullTradeItem() {
        // GIVEN
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(null);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_wrongTradeItem_nullSymbol() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, null, 100d, 20, new HashSet<>(Arrays.asList('a', 'b')));
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_wrongTradeItem_nullSetForFlags() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, null);
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

    @Test
    public void testFilterShouldNotFindItem_wrongTradeItem_emptyFlags() {
        // GIVEN
        TradeItem tradeItem = new TradeItem(1000L, "abc", 100d, 20, new HashSet<>());
        tradeItemFilterUnderTest = new TradeItemContainsFlagFilter("abc", 'c');

        // WHEN
        tradeItemFilterUnderTest.calculate(tradeItem);

        // THEN
        assertThat(tradeItemFilterUnderTest.getValue(), is(0d));
    }

}