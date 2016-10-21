package com.jpmorgan;

import com.jpmorgan.dao.CacheManager;
import com.jpmorgan.dao.MemoryCacheManager;
import com.jpmorgan.exceptions.StockException;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.services.stock.StockService;
import com.jpmorgan.services.stock.StockServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SuperSimpleStockApplication {

	private static final Logger log = LoggerFactory.getLogger(SuperSimpleStockApplication.class);

	private static final String CONSOLE_APP_PROFILE_ENABLED = "console.application.profile.enabled";

	private static void activateProfile(String profile, String message, boolean isEnableDefault, ConfigurableEnvironment environment) {
		String value = environment.getProperty(profile);
		if (StringUtils.isEmpty(value)) {
			value = Boolean.valueOf(isEnableDefault).toString();
		}

		if (value.equals(Boolean.toString(true))) {
			environment.addActiveProfile(profile);
			log.info("{} is enabled", message);
		} else {
			log.info("{} is disabled", message);
		}
	}

	@Profile(SuperSimpleStockApplication.CONSOLE_APP_PROFILE_ENABLED)
	public static void runConsoleApplication(ConfigurableApplicationContext context) throws StockException {

		StockServiceImpl stockService = (StockServiceImpl)context.getBean("stockService");
		CacheManager cacheManager = (MemoryCacheManager)context.getBean("memoryStockManager");

		Assert.notNull(stockService, "Stock service can't be empty!");
		Assert.notNull(cacheManager, "Cache Manager can't be empty!");

		log.info("\n\n");

		log.info("############################################################");
		log.info("#################### SUPER SIMPLE STOCK ####################");
		log.info("############################################################");

		log.info("\n");

		log.info("|-------------------------------------------------------------------------------------------------------------|");
		log.info("|-----------------------------------------------<< SAMPLE DATA >>---------------------------------------------|");
		log.info("|-------------------------------------------------------------------------------------------------------------|");
		log.info("|		Symbol		|		Type		|	Dividend	|	Fixed Dividend	|	Par Value	|	Ticker Price	|");
		log.info("|-------------------------------------------------------------------------------------------------------------|");
		log.info("|		TEA			|		COMMON		|		0		|					|		100		|		24.0		|");
		log.info("|		POP			|		COMMON		|		8		|					|		100		|		36.0		|");
		log.info("|		ALE			|		COMMON		|		23		|					|		60		|		89.0		|");
		log.info("|		GIN			|		PREFERRED	|		8		|		2%			|		100		|		74.0		|");
		log.info("|		JOE			|		COMMON		|		13		|					|		250		|		68.0		|");
		log.info("|-------------------------------------------------------------------------------------------------------------|");

		log.info("\n");

		log.info("###########-<< RESULTS >>-###########");

		log.info("\n");

		Map<String, Stock> stocks = cacheManager.getAllStocks();
		List<Trade> trades = cacheManager.getAllTrades();

		for (Stock stock : stocks.values()) {

			log.info("Stock => " + stock.toString());

			calculateDividendYield(stockService, stock);
			calculatePriceEarningRatio(stockService, stock);
			recordTrade(stockService, trades);
			calculateStockPrice(stockService, stock);
			calculateGBCEIndexAllShareIndex(stockService);

			log.info("\n");
		}

		log.info("#############-<< END >>-#############");
	}

	public static void main(String[] args) throws StockException {

		ConfigurableApplicationContext context = SpringApplication.run(SuperSimpleStockApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();

		// activate the profiles
		activateProfile(MemoryCacheManager.PROFILE_ENABLED, "Memory Cache Manager", true, environment);
		activateProfile(SuperSimpleStockApplication.CONSOLE_APP_PROFILE_ENABLED, "Console Application profile", true, environment);

		String[] activeProfiles = environment.getActiveProfiles();
		for (String profile : activeProfiles) {

			// run the console application
			if (profile.equals(SuperSimpleStockApplication.CONSOLE_APP_PROFILE_ENABLED)) {
				runConsoleApplication(context);
			}
		}
	}

	private static void calculateDividendYield(StockService stockService, Stock stock) {
		try {
			log.info("Calculate the Divided Yield => {}", stockService.calculateDividendYield(stock.getStockSymbol()));
		} catch (StockException e) {
			log.error("Error Calculating the Divided Yield => {}", e.getMessage());
		}
	}

	private static void calculatePriceEarningRatio(StockService stockService, Stock stock) {
		try {
			log.info("Calculate the P/E Ratio => {}", stockService.calculatePriceEarningRatio(stock.getStockSymbol()));
		} catch (StockException e) {
			log.error("Error Calculating the P/E Ratio => {}", e.getMessage());
		}
	}

	private static void recordTrade(StockService stockService, List<Trade> trades) {
		try {
			Trade trade = trades.get(0);
			log.info("Record a trade, with timestamp, quantity of shares, buy or sell indicator and price => {}", stockService.recordTrade(trade));
		} catch (StockException e) {
			log.error("Error Record a trade, with timestamp, quantity of shares, buy or sell indicator and price => {}", e.getMessage());
		}
	}

	private static void calculateStockPrice(StockService stockService, Stock stock) {
		try {
			log.info("Calculate price based on trades recorded in past 15 minutes => {}", stockService.calculateStockPrice(stock.getStockSymbol()));
		} catch (StockException e) {
			log.error("Error Calculating price based on trades recorded in past 15 minutes => {}", e.getMessage());
		}
	}

	private static void calculateGBCEIndexAllShareIndex(StockService stockService) {
		try {
			log.info("Calculate the GBCE All shares Index using the geometric means of prices for all stocks => {}", stockService.calculateGBCEIndexAllShareIndex());
		} catch (StockException e) {
			log.error("Error Calculating the GBCE All shares Index using the geometric means of prices for all stocks => {}", e.getMessage());
		}
	}
}
