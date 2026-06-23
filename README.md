# TeArrow

[![GitHub License](https://img.shields.io/github/license/Resend1298/tearrow)](LICENSE)
[![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/Resend1298/tearrow/maven.yml)](https://github.com/Resend1298/tearrow/actions/workflows/maven.yml)

A Telegram bot that replies with DeArrow title replacement when YouTube links are sent in a chat.

## Usage

Due to the nature of this bot, it needs to be able to read all messages in group chats.
Therefore, it is recommended to host the bot yourself due to privacy concerns.
Still, you can use [@tearrow_bot](https://t.me/tearrow_bot) for testing purposes or if you trust the developer.
Do note that the hosted bot doesn't have an SLA and may be offline at times.

### Self-hosting

#### Create a Telegram Bot

Go to [@BotFather](https://t.me/BotFather) on Telegram and create a new bot.
Follow the instructions to get your bot token.

After creating the bot, use the command `/mybots` at BotFather,
select your bot, go to "Bot Settings" -> "Group Privacy",
and disable "[Privacy Mode](https://core.telegram.org/bots/features#privacy-mode)".
This is necessary for the bot to read all messages in group chats.

## License

This project is licensed under the GNU Affero General Public License v3 or any later version of your choice.
See the [LICENSE](LICENSE) file for details.
