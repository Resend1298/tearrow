package me.pann.tearrow.telegram;

import me.pann.tearrow.service.TeArrowService;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.Optional;

public class TeArrowBot implements LongPollingSingleThreadUpdateConsumer {
	private final TeArrowService teArrowService;
	private final TelegramClient telegramClient;
	private static final String START_HELP_TEXT = """
			TeArrow replaces clickbait YouTube titles with DeArrow's crowdsourced alternatives.
			
			Send any message containing a YouTube link, and the bot will reply with the DeArrow title when one is available.
			
			This bot is free software under AGPL-3.0-or-later.
			You can find the source code at https://github.com/Resend1298/tearrow.
			
			Uses SponsorBlock data licensed used under CC BY-NC-SA 4.0 (https://creativecommons.org/licenses/by-nc-sa/4.0/) from https://sponsor.ajay.app/.
			""";
	private static final List<BotCommand> COMMANDS = List.of(
			new BotCommand("start", "Show introduction, usage, and license information"),
			new BotCommand("help", "Same as /start")
	);

	public TeArrowBot(TeArrowService teArrowService, TelegramClient telegramClient) {
		this.teArrowService = teArrowService;
		this.telegramClient = telegramClient;
	}

	@Override
	public void consume(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();

			if (messageText.startsWith("/start") || messageText.startsWith("/help")) {
				SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), START_HELP_TEXT);
				try {
					telegramClient.execute(sendMessage);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				return;
			}

			Optional<String> reply = teArrowService.createReply(messageText);

			if (reply.isPresent()) {
				SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), reply.get());
				// The only link in the reply is the attribution link,
				// so suppress the preview to avoid cluttering the chat with a link preview.
				sendMessage.setDisableWebPagePreview(true);
				try {
					telegramClient.execute(sendMessage);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void registerCommands() throws TelegramApiException {
		telegramClient.execute(new SetMyCommands(COMMANDS));
	}
}
