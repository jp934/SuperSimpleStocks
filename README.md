# SuperSimpleStocks
Super Simple Stocks is a demo application which manage the trades on set of stocks. The application was developed for the JP morgan technical test, please see the technical test details in the requirement section.

### Requirements

Provide working source code that will:

    a.	For a given stock:
    
        i.    Calculate the dividend yield.
        ii.   Calculate the P/E Ratio.
        iii.  Record a trade, with timestamp, quantity of shares, buy or sell indicator and price.
        iv.   Calculate Stock Price based on trades recorded in past 15 minutes.

    b.	Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

### Constraints

1.	Written in one of these languages:
    
    * Java, C#, C++, Python.
    
2.	No database or GUI is required, all data need only be held in memory.

3.	Formulas and data provided are simplified representations for the purpose of this exercise.

### Sample Data

Stock Symbol  | Type | Last Dividend | Fixed Dividend | Par Value
------------- | ---- | ------------: | :------------: | --------: 
TEA           | Common    | 0  |    | 100
POP           | Common    | 8  |    | 100
ALE           | Common    | 23 |    | 60
GIN           | Preferred | 8  | 2% | 100
JOE           | Common    | 13 |    | 250

### Prerequisites

*  Java version 1.7
*  Spring Framework Boot 1.4.1.RELEASE
*  IntelliJ version 15.0 (Preferred)

### Technical Design and Solution

To design the technical solution for Super Simple Stocks application, we have taken the service oriented architecture approach which provide the ability to focus on separation of concerns and reusability.

#### StockService 

The StockService class is a service that satisfy the concrete definition of implementing the Super Simple Stock application. It provides the list of methods that calculate the trades on set of stocks.

#### CacheManager 

The CacheManager class manage the data access layer and provide the concrete template definition cache management in the application. To satisfy one of the given application requirements, we have created the _**MemoryCacheManager**_. 

However, the data can also be persisted in one of the databases using the _**DBCacheManager**_.

#### StockException

The StockException is a custom exception class that provides the list of checked exception to provide the better exception handling in the application.

### Unit Test

To test the Super Simple Stocks core functionality, we have written some of the unit tests _**SuperSimpleStockApplicationTests**_.

```java
  testCalculateDividendYield
  testCalculatePriceEarningRatio
  testCalculateStockPrice
  testCalculateStockPriceWithTimeRange
  testRecordTrade
  testGBCEIndexAllShareIndex
```
To run the test using maven command on console:

    maven test

### How to use it

Super Simple Stocks is a spring based project and built using the latest version of maven. To setup and run the project, please follow the below instructions.

1.	Download or Checkout the project at either desktop or given location
2.	Import the project in one of the latest IDE's. We have used the IntelliJ version 15.0.
3.	Compile the project using the below maven command on console.
    
        maven clean package

    This will compile the project and create a new package _**supersimplestock-0.0.1-SNAPSHOT.jar**_
    
4.	To run the app with the given sample data, open the class _**SuperSimpleStockApplication** and execute the ```Run``` command.
     
