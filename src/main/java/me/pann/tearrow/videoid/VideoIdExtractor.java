package me.pann.tearrow.videoid;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoIdExtractor {
	/*
	 * Currently supported YouTube URL formats:
	 * - https://www.youtube.com/watch?v=qWNQUvIk954
	 * - https://www.youtube.com/watch?v=qWNQUvIk954&t=44s
	 * - https://youtu.be/qWNQUvIk954
	 * - https://youtu.be/qWNQUvIk954?t=44
	 */

	public static List<String> extractVideoIds(String text) {
		List<URL> urls = extractUrls(text);
		List<String> videoIds = new ArrayList<>();

		for (URL url : urls) {
			Optional<String> videoId = parseVideoIdFromUrl(url);
			videoId.ifPresent(videoIds::add);
		}

		return videoIds;
	}

	private static List<URL> extractUrls(String text) {
		Pattern urlPattern = Pattern.compile("https://[^\\s,，、。!！？)）]+");
		Matcher matcher = urlPattern.matcher(text);
		List<URL> urls = new ArrayList<>();

		while (matcher.find()) {
			try {
				URL url = new URI(matcher.group()).toURL();
				urls.add(url);
			} catch (URISyntaxException | MalformedURLException _) {
				// ignore invalid URLs
			}
		}

		return urls;
	}

	private static Optional<String> parseVideoIdFromUrl(URL url) {
		String protocol = url.getProtocol();
		String host = url.getHost();
		String path = url.getPath();
		String query = url.getQuery();

		if (!protocol.equals("https")) {
			return Optional.empty();
		}

		switch (host) {
			case "www.youtube.com":
				if (path.equals("/watch") && query != null) {
					String[] parameters = query.split("&");
					for (String parameter : parameters) {
						String[] keyValue = parameter.split("=");
						if (keyValue.length == 2 && keyValue[0].equals("v") && !keyValue[1].isEmpty()) {
							return Optional.of(keyValue[1]);
						}
					}
				}
				break;

			case "youtu.be":
				if (path.length() >= 2 && path.startsWith("/")) {
					return Optional.of(path.substring(1));
				}
				break;
		}

		return Optional.empty();
	}
}
