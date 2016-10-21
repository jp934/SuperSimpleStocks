package com.jpmorgan;

import com.jpmorgan.config.AppConfiguration;
import com.jpmorgan.dao.CacheManager;
import com.jpmorgan.dao.MemoryCacheManager;
import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.StockType;
import com.jpmorgan.model.Trade;
import com.jpmorgan.model.TradeType;
import com.jpmorgan.services.stock.StockServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
		TestConfiguration.class,
		AppConfiguration.class
}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(MemoryCacheManager.PROFILE_ENABLED)
public class SuperSimpleStockApplicationTests {

	@InjectMocks
	private StockServiceImpl stockService;

	@Mock
	private CacheManager memoryStockManager;

	@Resource(name = "stocks")
	private Map<String, Stock> stocks;

	@Resource(name= "stockTrades")
	private List<Trade> stockTrades;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCalculateDividendYield() throws StockException {

		// arrange
		Stock stock = new Stock("TEA", StockType.COMMON, 16.0f, 8.0f, 100, 56.45f);
		Mockito.when(memoryStockManager.getStockBySymbol(stock.getStockSymbol())).thenReturn(stock);

		// act
		double dividendYield = stockService.calculateDividendYield(stock.getStockSymbol());

		// assert
		Assert.assertEquals(roundOff(dividendYield), 0.28f, 0.001);
	}

	@Test
	public void testCalculatePriceEarningRatio() throws StockException {

		// arrange
		Stock stock = new Stock("POP", StockType.COMMON, 8.0f, 10.0f, 100, 15.9f);
		Mockito.when(memoryStockManager.getStockBySymbol(stock.getStockSymbol())).thenReturn(stock);

		// act
		double priceEarningRatio = stockService.calculatePriceEarningRatio(stock.getStockSymbol());

		// assert
		Assert.assertEquals(roundOff(priceEarningRatio), 31.6f, 0.001);
	}

	@Test
	public void testCalculateStockPrice() throws StockException {

		// arrange
		Mockito.when(memoryStockManager.getAllStocks()).thenReturn(stocks);
		Mockito.when(memoryStockManager.getAllTrades()).thenReturn(stockTrades);
		Mockito.when(memoryStockManager.getStockBySymbol("TEA")).thenReturn(stocks.get("TEA"));

		// act
		double totalStockPrice = stockService.calculateStockPriceWithTimeRange("TEA", 15);

		// assert
		Assert.assertEquals(roundOff(totalStockPrice), 33.8f, 0.001);
	}

	@Test
	public void testCalculateStockPriceWithTimeRange() throws StockException {

		// arrange
		Mockito.when(memoryStockManager.getAllStocks()).thenReturn(stocks);
		Mockito.when(memoryStockManager.getAllTrades()).thenReturn(stockTrades);
		Mockito.when(memoryStockManager.getStockBySymbol("TEA")).thenReturn(stocks.get("TEA"));

		// act
		double totalStockPrice = stockService.calculateStockPriceWithTimeRange("TEA", 10);

		// assert
		Assert.assertEquals(roundOff(totalStockPrice), 36.03f, 0.001);
	}

	@Test
	public void testRecordTrade() throws StockException {

		// arrange
		Stock stock = new Stock("ALE", StockType.COMMON, 18.0f, 40.0f, 500, 83.9f);
		Trade trade = new Trade(new Date(), 12, 14.f, TradeType.BUY, stock);
		Mockito.when(memoryStockManager.recordTrade(trade)).thenReturn(true);

		// act
		boolean recordTrade = stockService.recordTrade(trade);

		// assert
		Assert.assertEquals(recordTrade, true);
	}

	@Test
	public void testGBCEIndexAllShareIndex() throws StockException {

		// arrange
		Mockito.when(memoryStockManager.getAllTrades()).thenReturn(stockTrades);
		Mockito.when(memoryStockManager.getAllStocks()).thenReturn(stocks);
		for (String stockSymbol : stocks.keySet()) {
			Mockito.when(memoryStockManager.getStockBySymbol(stockSymbol)).thenReturn(stocks.get(stockSymbol));
		}

		// act
		double allShareIndex = stockService.calculateGBCEIndexAllShareIndex(15);

		// assert
		Assert.assertEquals(roundOff(allShareIndex), 1.0f, 0.001);
	}

	private double roundOff(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
