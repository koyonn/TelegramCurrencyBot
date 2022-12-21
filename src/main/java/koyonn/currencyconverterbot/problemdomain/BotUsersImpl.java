package koyonn.currencyconverterbot.problemdomain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class BotUsersImpl implements BotUsersContract {
	// Отображение, где ключ - id чата, а значение - название валюты
	private final Map<String, String> firstCurrency;
	// Отображение, где ключ - id чата, а значение - название валюты
	private final Map<String, String> secondCurrency;
	// Отображение, где ключ - id чата, а значение - булевое значeние,
	// отвечающие за подтверждение выбора валюты
	private final Map<String, Boolean> boolFirstCurrency;
	// Отображение, где ключ - id чата, а значение - булевое значeние,
	// отвечающие за подтверждение выбора валюты
	private final Map<String, Boolean> boolSecondCurrency;
	// Отображение, где ключ - id чата, а значение - название валюты
	private final Map<String, Double> valueOfExchange;
	// Множество, где хранятся id чатов пользователей
	private final Set<String> chatIdSet;

	BotUsersImpl() {
		firstCurrency = new HashMap<>();
		secondCurrency = new HashMap<>();
		boolFirstCurrency = new HashMap<>();
		boolSecondCurrency = new HashMap<>();
		valueOfExchange = new HashMap<>();
		chatIdSet = new HashSet<>();
	}

	/**
	 * Геттер экземпляра объекта, являющийся агрегатором данных пользователей
	 * telegram бота
	 *
	 * @return объект, инкапсулирующий данные пользователей и методы с ними
	 *         работающие
	 */
	public static BotUsersImpl getBotUser() {
		return new BotUsersImpl();
	}

	@Override
	public String getFirstCurrency(String chatId) {
		return firstCurrency.get(chatId);
	}

	@Override
	public String getSecondCurrency(String chatId) {
		return secondCurrency.get(chatId);
	}

	@Override
	public boolean getBoolFirstCurrency(String chatId) {
		return boolFirstCurrency.get(chatId);
	}

	@Override
	public boolean getBoolSecondCurrency(String chatId) {
		return boolSecondCurrency.get(chatId);
	}

	@Override
	public double getValueOfExchange(String chatId) {
		return valueOfExchange.get(chatId);
	}

	@Override
	public void setFirstCurrency(String chatId, String name) {
		firstCurrency.put(chatId, name);
	}

	@Override
	public void setSecondCurrency(String chatId, String name) {
		secondCurrency.put(chatId, name);
	}

	@Override
	public void setBoolFirstCurrency(String chatId, Boolean name) {
		boolFirstCurrency.put(chatId, name);
	}

	@Override
	public void setBoolSecondCurrency(String chatId, Boolean name) {
		boolSecondCurrency.put(chatId, name);
	}

	@Override
	public void setValueOfExchange(String chatId, Double value) {
		valueOfExchange.put(chatId, value);
	}

	@Override
	public boolean setChatId(String chatId) {
		return chatIdSet.add(chatId);
	}
}
