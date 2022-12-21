package koyonn.currencyconverterbot.constants;

import java.util.HashSet;
import java.util.Set;

import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;

public class Constants {
	Constants() {
		throw new IllegalStateException("Utility class");
	}

	// Официальный курс белорусского рубля на сегодня
	private static final String CURRENCY_EXCHANGE_RATE_URL = "https://www.nbrb.by/api/exrates/rates?periodicity=0";
	// Белорусский рубль
	private static final NBRBCurrency BYN = new NBRBCurrency(0, "BYN", 1, "Белорусских рублей", 1);
	// Множество популярный валют
	private static final Set<String> MAIN_CURRENCY_ABBREVIATION = new HashSet<>();
	// Инициализация валют
	static {
		MAIN_CURRENCY_ABBREVIATION.add("USD");
		MAIN_CURRENCY_ABBREVIATION.add("EUR");
		MAIN_CURRENCY_ABBREVIATION.add("CNY");
		MAIN_CURRENCY_ABBREVIATION.add("PLN");
		MAIN_CURRENCY_ABBREVIATION.add("BYN");
		MAIN_CURRENCY_ABBREVIATION.add("RUB");
	}

	/**
	 * Геттер белорусского рубля
	 *
	 * @return бел. рубль
	 */
	public static NBRBCurrency getBYN() {
		return BYN;
	}

	/**
	 * Геттер ссылки для получения курса валют
	 *
	 * @return ссылка
	 */
	public static String getCurrencyExchangeRateURL() {
		return CURRENCY_EXCHANGE_RATE_URL;
	}

	/**
	 * Геттер множества популярных валют
	 *
	 * @return множество валют
	 */
	public static Set<String> getMainCurrencyAbbreviation() {
		return MAIN_CURRENCY_ABBREVIATION;
	}
}