package me.pann.tearrow;

import me.pann.tearrow.dearrow.DeArrowClient;
import me.pann.tearrow.service.TeArrowService;
import me.pann.tearrow.telegram.TeArrowBot;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
	static void main() {
		try {
			String botToken = System.getenv("TELEGRAM_BOT_TOKEN");

			TeArrowBot bot = new TeArrowBot(new TeArrowService(new DeArrowClient()), new OkHttpTelegramClient(botToken));
			bot.registerCommands();

			TelegramBotsLongPollingApplication botApplication = new TelegramBotsLongPollingApplication();
			botApplication.registerBot(botToken, bot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
