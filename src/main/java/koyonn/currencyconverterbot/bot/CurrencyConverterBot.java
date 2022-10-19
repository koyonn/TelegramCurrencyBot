package koyonn.currencyconverterbot.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import koyonn.currencyconverterbot.constants.Constants;
import koyonn.currencyconverterbot.problemdomain.BotUsersContract;
import koyonn.currencyconverterbot.problemdomain.impl.NBRBCurrency;
import koyonn.currencyconverterbot.service.CurrencyConverterContract;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CurrencyConverterBot extends TelegramLongPollingBot {

    // Экземпляр конвертера
    private final CurrencyConverterContract currencyConverter;
    // Список популярный валют
    private final Set<String> popularCurrancies;
    // экземпляр объекта, который содержит данные о пользователях бота: 
    // id чата и выборе валют для конвертации.
    private final BotUsersContract users;

    private final static String TOKEN = "12345678";

    private final static String BOT_NAME = "knnCCBot";

    /*
     * Конструктор бота
     */
    public CurrencyConverterBot() {
        currencyConverter = CurrencyConverterContract.getInstance();
        popularCurrancies = Constants.getMainCurrencyAbbreviation();
        users = BotUsersContract.getInstance();
    }

    /**
     * Геттер токена
     *
     * @return токен
     */
    @Override
    public String getBotToken() {
        return TOKEN;
    }

    /**
     * Геттер имени бота
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Метод, куда поступают апдейты для бота
     *
     * @param update входящее обновление
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }
    }

    /**
     * Обработка сообщений
     *
     * @param message сообщение
     */
    private void handleMessage(Message message) {
        String chatId = message.getChatId().toString();
        if (message.hasText() && message.hasEntities()) {
            // Поиск команды
            Optional<MessageEntity> commandEntity
                    = message.getEntities()
                            .stream()
                            .filter(e -> "bot_command".equals(e.getType()))
                            .findFirst();
            if (commandEntity.isPresent()) {
                // Обрезаем строку, так как пользователь вместе с командой может ввести и лишний текст
                String command = message.getText().substring(
                        commandEntity.get().getOffset(),
                        commandEntity.get().getLength()
                );
                switch (command) {
                    case "/set_currency":
                        // Кнопки
                        sendMessage(chatId,
                                "Из какой в какую валюты конвертировать?",
                                getInlineKeyboard());
                        break;
                    case "/get_exchange_rates_full":
                        sendMessage(chatId, currencyConverter.getCurrencyRatesFull());
                        break;
                    case "/get_exchange_rates_short":
                        sendMessage(chatId, currencyConverter.getCurrencyRatesShort());
                        break;
                    default:
                        sendMessage(chatId, "Команда не обнаружена");
                }
            }
            // Обработка сообщения после ввода суммы конвертации
        } else if (message.hasText() && users.getBoolFirstCurrency(chatId) && users.getBoolSecondCurrency(chatId)) {
            users.setValueOfExchange(chatId,
                    Double.parseDouble(message.getText())
            );
            sendMessage(chatId, currencyConverter.getCurrencyExchangeRates(
                    users.getFirstCurrency(chatId),
                    users.getSecondCurrency(chatId),
                    users.getValueOfExchange(chatId)
            ));
            users.setBoolFirstCurrency(chatId, false);
            users.setBoolSecondCurrency(chatId, false);
            users.setValueOfExchange(chatId, 0.0);
        }
    }

    /**
     * Обработка нажатий на inline-клавиатуру
     *
     * @param callbackQuery нажатие кнопки inline-клавиатуры
     */
    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String chatId = message.getChatId().toString();
        String action = callbackQuery.getData();
        // Если пользователь новый, то зададим стандартные значения
        if (users.setChatId(chatId)) {
            users.setBoolFirstCurrency(chatId, false);
            users.setBoolSecondCurrency(chatId, false);
            users.setValueOfExchange(chatId, 0.0);
        }
        if (users.getBoolFirstCurrency(chatId)) {
            users.setSecondCurrency(chatId, action);
            users.setBoolSecondCurrency(chatId, true);
        }
        if (!users.getBoolSecondCurrency(chatId)) {
            users.setFirstCurrency(chatId, action);
            users.setBoolFirstCurrency(chatId, true);
        }
        if (users.getBoolFirstCurrency(chatId) && users.getBoolSecondCurrency(chatId)) {
            sendMessage(chatId,
                    "Вы выбрали " + users.getFirstCurrency(chatId) + " и " + users.getSecondCurrency(chatId) + "\n"
                    + "Введите сумму: ");
        }
    }

    /**
     * Метод для конструирования inline-клавиатуры
     *
     * @return inline-клавиатура
     */
    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (String curName : popularCurrancies) {
            NBRBCurrency currency = (NBRBCurrency) currencyConverter.getCurrencyMap().get(curName);
            InlineKeyboardButton button
                    = new InlineKeyboardButton(currency.getAbbreviation()
                    );  
            button.setCallbackData(currency.getAbbreviation()
            );
            row.add(button);
        }
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    /**
     * Метод для отправки сообщений от бота в чате
     *
     * @param chatId id чата
     * @param text   текст сообщения
     */
    private void sendMessage(String chatId, String text) {
        try {
            SendMessage sm = new SendMessage(chatId, text);
            execute(sm);
        } catch (TelegramApiException ex) {
            ex.getMessage();
        }
    }

    /**
     * Метод для отправки сообщений от бота в чате и inline-клавиатуры
     *
     * @param chatId id чата
     * @param text   текст сообщения
     */
    private void sendMessage(String chatId, String text, InlineKeyboardMarkup ikm) {
        try {
            SendMessage sm = new SendMessage(chatId, text);
            sm.setReplyMarkup(ikm);
            execute(sm);
        } catch (TelegramApiException ex) {
            ex.getMessage();
        }
    }
}
