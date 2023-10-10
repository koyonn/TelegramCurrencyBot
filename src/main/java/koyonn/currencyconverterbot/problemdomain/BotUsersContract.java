package koyonn.currencyconverterbot.problemdomain;

public interface BotUsersContract {

	static BotUsersContract getInstance() {
		return new BotUsersImpl();
	}

	/**
	 * Геттер валюты, из которой будет конвертация
	 *
	 * @param chatId id чата
	 * @return название валюты
	 */
	public String getFirstCurrency(String chatId);

	/**
	 * Геттер валюты, в которую будет конвертация
	 *
	 * @param chatId id чата
	 * @return название валюты
	 */
	public String getSecondCurrency(String chatId);

	/**
	 * Геттер проверки выбора первой валюты
	 *
	 * @param chatId id чата
	 * @return true, если валюта выбрана
	 */
	public boolean getBoolFirstCurrency(String chatId);

	/**
	 * Геттер проверки выбора второц валюты
	 *
	 * @param chatId id чата
	 * @return true, если валюта выбрана
	 */
	public boolean getBoolSecondCurrency(String chatId);

	/**
	 * Получить размер конвертируемой валюты
	 *
	 * @param chatId id чата
	 * @return размер валюты
	 */
	public double getValueOfExchange(String chatId);

	/**
	 * Сеттер валюты, из которой будет конвертация
	 *
	 * @param chatId id чата
	 * @param name   название валюты
	 */
	public void setFirstCurrency(String chatId, String name);

	/**
	 * Сеттер валюты, в которую будет конвертация
	 *
	 * @param chatId id чата
	 * @param name   название валюты
	 */
	public void setSecondCurrency(String chatId, String name);

	/**
	 * Сеттер проверки выбора первой валюты
	 *
	 * @param chatId id чата
	 * @param name   название валюты
	 */
	public void setBoolFirstCurrency(String chatId, Boolean name);

	/**
	 * Сеттер проверки выбора второй валюты
	 *
	 * @param chatId id чата
	 * @param name   название валюты
	 */
	public void setBoolSecondCurrency(String chatId, Boolean name);

	/**
	 * @param chatId id чата
	 * @param value  величина валюты
	 */
	public void setValueOfExchange(String chatId, Double value);

	/**
	 * Добавить id чата в хранилище
	 *
	 * @param chatId id чата
	 * @return true, если значение добавлено
	 */
	public boolean setChatId(String chatId);
}
