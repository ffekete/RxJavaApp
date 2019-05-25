package com.streamtestapp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.streamtestapp.filter.AverageTradeValueFilter;
import com.streamtestapp.filter.MaxTradeValueFilter;
import com.streamtestapp.filter.TradeItemContainsFlagFilter;
import com.streamtestapp.filter.TradeItemFilter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StreamProcessorIntegrationTest {

    private final TestStream testStream = new TestStream();
    private final List<TradeItemFilter> tradeItemFilters = new ArrayList<>();
    private final StreamProcessor streamProcessorUnderTest = new StreamProcessor(tradeItemFilters);

    @BeforeMethod
    public void setUp() {
        tradeItemFilters.clear();
    }

    @Test
    public void testMaxTradeValueValueForAbc() {
        TradeItemFilter maxTradeValueFilterForAbc = new MaxTradeValueFilter("abc");

        tradeItemFilters.add(maxTradeValueFilterForAbc);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(maxTradeValueFilterForAbc.getValue(), is(100000.0d));
    }

    @Test
    public void testMaxTradeValueValueForNullSymbol() {
        TradeItemFilter maxTradeValueFilterForAbc = new MaxTradeValueFilter(null);

        tradeItemFilters.add(maxTradeValueFilterForAbc);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(maxTradeValueFilterForAbc.getValue(), is(0.0d));
    }

    @Test
    public void testMaxTradeValueValueForUnknownSymbol() {
        TradeItemFilter maxTradeValueFilterForAbc = new MaxTradeValueFilter("abc%");

        tradeItemFilters.add(maxTradeValueFilterForAbc);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(maxTradeValueFilterForAbc.getValue(), is(0.0d));
    }

    @Test
    public void testMaxTradeValueValueForAbc_emptyInputList() {
        TradeItemFilter maxTradeValueFilterForAbc = new MaxTradeValueFilter("abc");

        tradeItemFilters.add(maxTradeValueFilterForAbc);

        Observable<TradeItem> observable = Observable.from(new ArrayList<>());
        streamProcessorUnderTest.subscribe(observable);

        assertThat(maxTradeValueFilterForAbc.getValue(), is(0.0d));
    }

    @Test
    public void testMaxTradeValueForXyzLn() {
        TradeItemFilter maxTradeValueFilterForXyzln = new MaxTradeValueFilter("XYZ LN");

        tradeItemFilters.add(maxTradeValueFilterForXyzln);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(maxTradeValueFilterForXyzln.getValue(), is(23.4 * 1045));
    }

    @Test
    public void testContainsCharacterForXyzLn() {
        TradeItemFilter tradeItemFilter = new TradeItemContainsFlagFilter("XYZ LN", 'C');

        tradeItemFilters.add(tradeItemFilter);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(tradeItemFilter.getValue(), is(1d));
    }

    @Test
    public void testContainsCharacter_emptySymbol() {
        TradeItemFilter tradeItemFilter = new TradeItemContainsFlagFilter("", 'C');

        tradeItemFilters.add(tradeItemFilter);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(tradeItemFilter.getValue(), is(0d));
    }

    @Test
    public void testContainsCharacter_nullSymbol() {
        TradeItemFilter tradeItemFilter = new TradeItemContainsFlagFilter(null, 'C');

        tradeItemFilters.add(tradeItemFilter);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(tradeItemFilter.getValue(), is(0.0d));
    }

    @Test
    public void testContainsCharacterForXyzLn_nullCharacter() {
        TradeItemFilter tradeItemFilter = new TradeItemContainsFlagFilter("XYZ LN", null);

        tradeItemFilters.add(tradeItemFilter);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(tradeItemFilter.getValue(), is(0d));
    }

    @Test
    public void testContainsCharacterForXyzLn_noCharacterPresent() {
        TradeItemFilter tradeItemFilter = new TradeItemContainsFlagFilter("XYZ LN", '^');

        tradeItemFilters.add(tradeItemFilter);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(tradeItemFilter.getValue(), is(0d));
    }

    @Test
    public void testCombinedTestForMultipleFilters() {
        TradeItemFilter maxTradeValueFilterForXyzln = new MaxTradeValueFilter("XYZ LN");
        TradeItemFilter averageTradeValueFilter = new AverageTradeValueFilter("XYZ LN");
        TradeItemFilter containsCharacterFilter = new TradeItemContainsFlagFilter("mno", '0');

        tradeItemFilters.add(maxTradeValueFilterForXyzln);
        tradeItemFilters.add(averageTradeValueFilter);
        tradeItemFilters.add(containsCharacterFilter);

        Observable<TradeItem> observable = Observable.from(testStream.values);
        streamProcessorUnderTest.subscribe(observable);

        assertThat(maxTradeValueFilterForXyzln.getValue(), is(23.4 * 1045));
        assertThat(averageTradeValueFilter.getValue(), is((-128.0 + 23.4) / 2d));
        assertThat(containsCharacterFilter.getValue(), is(2d));
    }

    private class TestStream {

        private final List<TradeItem> values = ImmutableList.<TradeItem>builder()
                .add(new TradeItem(1000L, "abc", 23.4, 534, ImmutableSet.<Character>builder().add('A').build()))
                .add(new TradeItem(1001L, "abc", 100d, 1000, ImmutableSet.<Character>builder().add('Z').build()))
                .add(new TradeItem(1002L, "XYZ LN", -128d, Integer.MAX_VALUE, ImmutableSet.<Character>builder().add('C').build()))
                .add(new TradeItem(1003L, "jkl", 0.0, Integer.MIN_VALUE, ImmutableSet.<Character>builder().add('F').build()))
                .add(new TradeItem(1004L, "mno", Double.MAX_VALUE, 0, ImmutableSet.<Character>builder().add('0').build()))
                .add(new TradeItem(1004L, "mno", 1d, 2, ImmutableSet.<Character>builder().add('0').build()))
                .add(new TradeItem(1004L, "pqr", Double.MIN_VALUE, 0, ImmutableSet.<Character>builder().add('%').build()))
                .add(new TradeItem(1005L, "XYZ LN", 23.4, 1045, ImmutableSet.<Character>builder().add('D').build()))
                .build();
    }

}