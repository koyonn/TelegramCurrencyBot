package koyonn.currencyconverterbot.constants;

import java.util.HashSet;
import java.util.Set;

import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;

public class Constants {

    // Официальный курс белорусского рубля на сегодня
    private final static String CURRENCY_EXCHANGE_RATE_URL = "https://www.nbrb.by/api/exrates/rates?periodicity=0";
    // Белорусский рубль
    private final static NBRBCurrency BYN
            = new NBRBCurrency(0, "BYN", 1, "Белорусских рублей", 1);
    // Множество популярный валют
    private final static Set<String> MAIN_CURRENCY_ABBREVIATION = new HashSet<>() {
        {
            add("USD");
            add("EUR");
            add("CNY");
            add("PLN");
            add("BYN");
            add("RUB");
        }
    };

    /**
     * Геттер белорусскиого рубля
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
