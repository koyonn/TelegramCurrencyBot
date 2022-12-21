package koyonn.currencyconverterbot.service;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import koyonn.currencyconverterbot.constants.Constants;
import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;

public class NBRBCurrencyServiceTest {
	@Test
	public void getCurrencyRatesTest() {
		Map<String, NBRBCurrency> testCurRates = new NBRBCurrencyService().getCurrencyRatesList();
		for(var cur : Constants.getMainCurrencyAbbreviation()) {
			assertEquals(cur, testCurRates.get(cur).getAbbreviation());
		}
	}
}
