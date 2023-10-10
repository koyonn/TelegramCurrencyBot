package koyonn.currencyconverterbot.buildlogic;

import koyonn.currencyconverterbot.bot.CurrencyConverterBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BuildLogic {

	private static final Logger logger = LoggerFactory.getLogger(BuildLogic.class);

	BuildLogic() {
		throw new IllegalStateException("Utility class");
	}

	public static void buildBot() {
		try {
			// Создание бота
			CurrencyConverterBot bot = new CurrencyConverterBot();
			// Регистрация в TelegramBotsApi. Создается отдельный поток, который ,eltn
			// непрерывно отправлять сообщения на telegram getUpdates и если придет update
			// то он вызовет onUpdateReceived(Update update)
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException exc) {
			logger.error(exc.getMessage(), exc);
		}
	}
}
