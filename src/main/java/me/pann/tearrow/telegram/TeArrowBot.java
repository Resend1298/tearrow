package me.pann.tearrow.telegram;

import me.pann.tearrow.service.TeArrowService;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Optional;

public class TeArrowBot implements LongPollingSingleThreadUpdateConsumer {
	private final TeArrowService teArrowService;
	private final TelegramClient telegramClient;

	public TeArrowBot(TeArrowService teArrowService, TelegramClient telegramClient) {
		this.teArrowService = teArrowService;
		this.telegramClient = telegramClient;
	}

	@Override
	public void consume(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();

			Optional<String> reply = teArrowService.createReply(messageText);

			if (reply.isPresent()) {
				SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), reply.get());
				// The only link in the reply is the attribution link,
				// so suppress the preview to avoid cluttering the chat with a link preview.
				sendMessage.setDisableWebPagePreview(true);
				try {
					telegramClient.execute(sendMessage);
				} catch (org.telegram.telegrambots.meta.exceptions.TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
