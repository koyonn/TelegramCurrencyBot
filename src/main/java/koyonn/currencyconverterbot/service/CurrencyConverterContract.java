package koyonn.currencyconverterbot.service;

import java.util.Map;

import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;

public interface CurrencyConverterContract {

    /**
     * Получить экземпляр конвертера валют
     *
     * @return конвертер валют
     */
    static CurrencyConverterContract getInstance() {
        return new CurrencyConverterImpl();
	}

    /**
     * Получить валюты и их параметры
     *
     * @return отображение, где значения - валюты
     */
    Map<String, NBRBCurrency> getCurrencyMap();

    /**
     * Получить полный список валют и их курсов обмена
     *
     * @return список курсов валют
     */
    String getCurrencyRatesFull();

    /**
     * Получить краткий список валют и их курсов обмена
     *
     * @return список курсов валют
     */
    String getCurrencyRatesShort();

    /**
     * Метод для конвертации одной валюты в другу
     *
     * @param originalCurName из какой валюты
     * @param targetCurName   в какую валюты
     * @param value           сумма
     * @return
     */
    String getCurrencyExchangeRates(String originalCurName, String targetCurName, double value);
}
