# RxJavaApp

This library helps processing an input stream of TradeItems.
TradeItemFilters are attached to the main StreamProcessor, these filters are continuously running on the incoming TradeItems and running the
defined calculations.

To use simply import StreamProcessor to your project and subscribe to an RxJava Observable.
Create and add custom TradeItemFilter by extending interface TradeItemFilter.

