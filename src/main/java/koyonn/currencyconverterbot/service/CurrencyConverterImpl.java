package koyonn.currencyconverterbot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import koyonn.currencyconverterbot.constants.Constants;
import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;

public class CurrencyConverterImpl implements CurrencyConverterContract {
	// Отображение, где ключи - аббревиатуры валют, а значения - валюты
	private final Map<String, NBRBCurrency> currencyMap;
	private final NBRBCurrencyService ccs;

	CurrencyConverterImpl() {
		ccs = new NBRBCurrencyService();
		this.currencyMap = ccs.getCurrencyRatesList();
	}

	@Override
	public Map<String, NBRBCurrency> getCurrencyMap() {
		Map<String, NBRBCurrency> copyOfCurrencyMap = new HashMap<>();
		currencyMap.forEach(copyOfCurrencyMap::put);
		return copyOfCurrencyMap;
	}

	@Override
	public String getCurrencyRatesFull() {
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry<String, NBRBCurrency>> set = currencyMap.entrySet();
		set.forEach(e -> {
			long scale = e.getValue()
			              .getScale();
			String curName = e.getValue()
			                  .getCurName();
			double officialRate = e.getValue()
			                       .getOfficialRate();
			sb.append(String.format("%d %s = %.2f %s", scale, curName, officialRate, Constants.getBYN()
			                                                                                  .getCurName())
			        + "\n");
		});
		return new String(sb);
	}

	@Override
	public String getCurrencyRatesShort() {
		StringBuilder sb = new StringBuilder();
		Constants.getMainCurrencyAbbreviation()
		         .forEach(e -> {
			         if (!e.equals("BYN")) {
				         long scale = currencyMap.get(e)
				                                 .getScale();
				         String curName = currencyMap.get(e)
				                                     .getCurName();
				         double officialRate = currencyMap.get(e)
				                                          .getOfficialRate();
				         sb.append(String.format("%d %s = %.2f %s", scale, curName, officialRate, Constants.getBYN()
				                                                                                           .getCurName())
				                 + "\n");
			         }
		         });
		return new String(sb);
	}

	@Override
	public String getCurrencyExchangeRates(String originalAbbreviation, String targetAbbreviation, double value) {
		double originalRate = currencyMap.get(originalAbbreviation)
		                                 .getOfficialRate();
		double originalScale = currencyMap.get(originalAbbreviation)
		                                  .getScale();
		double targetRate = currencyMap.get(targetAbbreviation)
		                               .getOfficialRate();
		double targetScale = currencyMap.get(targetAbbreviation)
		                                .getScale();
		double result = (targetScale * (value * originalRate / originalScale)) / targetRate;
		return String.format("%.2f %s = %.2f %s", value, currencyMap.get(originalAbbreviation)
		                                                            .getCurName(),
		        result, currencyMap.get(targetAbbreviation)
		                           .getCurName());
	}
}
