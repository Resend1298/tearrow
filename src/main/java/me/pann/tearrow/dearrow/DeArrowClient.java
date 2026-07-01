package me.pann.tearrow.dearrow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

public class DeArrowClient {
	private final HttpClient client;

	public DeArrowClient() {
		client = HttpClient.newHttpClient();
	}

	public Optional<String> findTitle(String videoId) {
		Optional<URI> queryUri = buildUri(videoId);
		if (queryUri.isEmpty()) {
			return Optional.empty();
		}

		return queryTitle(queryUri.get());
	}

	private Optional<URI> buildUri(String videoId) {
		try {
			return Optional.of(new URI("https://sponsor.ajay.app/api/branding?videoID=" + videoId));
		} catch (java.net.URISyntaxException _) {
			// Ignore invalid URIs (likely due to invalid video IDs)
			return Optional.empty();
		}
	}

	private Optional<String> queryTitle(URI uri) {
		HttpRequest request = HttpRequest.newBuilder().uri(uri).timeout(Duration.ofSeconds(10)).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) {
				return Optional.empty();
			}

			return parseTitle(new JSONObject(response.body()));
		} catch (IOException _) {
			return Optional.empty();
		} catch (InterruptedException _) {
			Thread.currentThread().interrupt();
			return Optional.empty();
		}
	}

	private Optional<String> parseTitle(JSONObject responseJson) {
		if (!responseJson.has("titles")) {
			return Optional.empty();
		}
		JSONArray titles = responseJson.getJSONArray("titles");
		if (titles.isEmpty()) {
			return Optional.empty();
		}
		JSONObject firstTitle = titles.getJSONObject(0);

		String title = firstTitle.getString("title");
		boolean original = firstTitle.getBoolean("original");
		int votes = firstTitle.getInt("votes");
		boolean locked = firstTitle.getBoolean("locked");

		if (!original && (locked || votes >= 0)) {
			return Optional.of(title);
		} else {
			return Optional.empty();
		}
	}
}