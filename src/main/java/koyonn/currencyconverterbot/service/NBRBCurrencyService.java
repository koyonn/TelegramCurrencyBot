package koyonn.currencyconverterbot.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import koyonn.currencyconverterbot.buildlogic.BuildLogic;
import koyonn.currencyconverterbot.constants.Constants;
import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NBRBCurrencyService {
	private static final Logger logger = LoggerFactory.getLogger(NBRBCurrencyService.class);
	// Отображение, где ключ - аббревиатура валюты, а значение - валюта
	private final Map<String, NBRBCurrency> currencyRatesList;

	NBRBCurrencyService() {
		this.currencyRatesList = getCurrencyRates();
	}

	/**
	 * Геттер валют
	 *
	 * @return отображение, где хранятся валюты
	 */
	Map<String, NBRBCurrency> getCurrencyRatesList() {
		return currencyRatesList;
	}

	/**
	 * Метод, где используя Нацбанк РБ API, получаем json-объект со всеми
	 * валютами и их параметрами и копируем все значения в Map
	 *
	 * @return отображение, где значениями выступают валюты
	 */
	private Map<String, NBRBCurrency> getCurrencyRates() {
		Map<String, NBRBCurrency> hashMap = new HashMap<>();
		try {
			final URL url = new URL(Constants.getCurrencyExchangeRateURL());
			final HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				logger.error("HttpResponseCode: {}", responseCode);
				throw new RuntimeException();
			} else {
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new InputStreamReader(url.openStream()));
				JSONArray jsonArray = (JSONArray) obj;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					logger.debug("Валюта: {}", jsonObject);
					hashMap.put((String) jsonObject.get("Cur_Abbreviation"),
					        new NBRBCurrency((Long) jsonObject.get("Cur_ID"),
					                         (String) jsonObject.get("Cur_Abbreviation"),
					                         (Long) jsonObject.get("Cur_Scale"), (String) jsonObject.get("Cur_Name"),
					                         (Double) jsonObject.get("Cur_OfficialRate")));
				}
				hashMap.put("BYN", Constants.getBYN());
				hashMap.remove("XDR");
			}
		} catch (ParseException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return hashMap;
	}
}
