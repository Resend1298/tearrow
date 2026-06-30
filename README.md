# TeArrow

[![GitHub License](https://img.shields.io/github/license/Resend1298/tearrow)](LICENSE)
[![Maven CI](https://img.shields.io/github/actions/workflow/status/Resend1298/tearrow/maven.yml?label=maven)](https://github.com/Resend1298/tearrow/actions/workflows/maven.yml)
[![Docker](https://img.shields.io/github/actions/workflow/status/Resend1298/tearrow/docker-publish.yml?label=docker)](https://github.com/Resend1298/tearrow/actions/workflows/docker-publish.yml)
[![wakatime](https://wakatime.com/badge/github/Resend1298/tearrow.svg)](https://wakatime.com/badge/github/Resend1298/tearrow)

A Telegram bot that replies with DeArrow title replacement when YouTube links are sent in a chat.

## Usage

Due to the nature of this bot, it needs to be able to read all messages in group chats.
Therefore, it is recommended to host the bot yourself due to privacy concerns.

### Self-hosting

#### Create a Telegram Bot

Go to [@BotFather](https://t.me/BotFather) on Telegram and create a new bot.
Follow the instructions to get your bot token.

After creating the bot, use the command `/mybots` at BotFather,
select your bot, go to "Bot Settings" -> "Group Privacy",
and disable "[Privacy Mode](https://core.telegram.org/bots/features#privacy-mode)".
This is necessary for the bot to read all messages in group chats.

#### Deploy the Bot

Docker Compose is the recommended way to deploy the bot.

First, prepare a Linux server with Docker and Docker Compose installed.
Then, choose a desired docker image tag, options including:

- `vx.y.z`: a specific version, for example `v1.2.3`. Recommended for production use.
- `latest`: points to the latest `vx.y.z`.
- `master`: points to the latest commit on the `master` branch.

After getting the bot token and choosing a docker image tag,
run one of the following commands to download the `compose.yaml` template file depending on your choice of docker image
tag:

```shell
# For a specific version
mkdir tearrow
cd tearrow
# Replace `vx.y.z` with the desired version tag
wget https://raw.githubusercontent.com/Resend1298/tearrow/refs/tags/vx.y.z/compose.yaml
```

```shell
# For the latest release
mkdir tearrow
cd tearrow
# Check the latest release tag on GitHub, then use that tag here
wget https://raw.githubusercontent.com/Resend1298/tearrow/refs/tags/vx.y.z/compose.yaml
```

```shell
# For the latest commit on the master branch
mkdir tearrow
cd tearrow
wget https://raw.githubusercontent.com/Resend1298/tearrow/refs/heads/master/compose.yaml
```

Then, edit the downloaded `compose.yaml` template file.
Replace `vx.y.z` with your chosen docker image tag, such as `v1.2.3`, `latest`, or `master`,
and replace `YOUR_BOT_TOKEN` with your bot token.

Finally, run the following command to start the bot:

```shell
docker compose up -d
```

### Use the hosted bot

You can use the hosted bot [@tearrow_bot](https://t.me/tearrow_bot) for testing purposes or if you trust the developer.
Add it to your group to use it.

Do note that the hosted bot doesn't have an SLA and may be offline at times.

## License

This project is licensed under the GNU Affero General Public License v3 or any later version of your choice.
See the [LICENSE](LICENSE) file for details.

## Third-Party Licenses

This project bundles a number of third-party dependencies.
Their licenses are documented at [`src/main/resources/META-INF/third-party-licenses`](src/main/resources/META-INF/third-party-licenses):

- `THIRD-PARTY.txt`: the list of dependencies and their licenses
- `licenses.xml`: a machine-readable manifest mapping each dependency to its license file
- `licenses/`: the full text of each license

The same files are bundled into the fat jar and Docker image under `META-INF/third-party-licenses/`.

## Data Attribution

This bot retrieves data from the [DeArrow](https://dearrow.ajay.app/) project through the SponsorBlock API.
The DeArrow database is a separate work from the dependencies above and is licensed under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/).

> Uses SponsorBlock data licensed used under [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/) from https://sponsor.ajay.app/.
