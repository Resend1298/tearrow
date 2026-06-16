package me.pann.tearrow.videoid;

import java.net.URI;
import java.net.URISyntaxException;
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
		List<URI> uris = extractUris(text);
		List<String> videoIds = new ArrayList<>();

		for (URI uri : uris) {
			Optional<String> videoId = parseVideoIdFromUri(uri);
			videoId.ifPresent(videoIds::add);
		}

		return videoIds;
	}

	private static List<URI> extractUris(String text) {
		Pattern uriPattern = Pattern.compile("https://[^\\s,，、。!！？)）]+");
		Matcher matcher = uriPattern.matcher(text);
		List<URI> uris = new ArrayList<>();

		while (matcher.find()) {
			try {
				URI uri = new URI(matcher.group());
				uris.add(uri);
			} catch (URISyntaxException _) {
				// ignore invalid URIs
			}
		}

		return uris;
	}

	private static Optional<String> parseVideoIdFromUri(URI uri) {
		String scheme = uri.getScheme();
		String host = uri.getHost();
		String path = uri.getPath();
		String query = uri.getQuery();

		if (!scheme.equals("https") || host == null) {
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
