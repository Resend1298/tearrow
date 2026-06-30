package me.pann.tearrow.service;

import me.pann.tearrow.dearrow.DeArrowClient;
import me.pann.tearrow.videoid.VideoIdExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeArrowService {
	private final DeArrowClient deArrowClient;

	public TeArrowService(DeArrowClient deArrowClient) {
		this.deArrowClient = deArrowClient;
	}

	public Optional<String> createReply(String message) {
		List<String> videoIds = VideoIdExtractor.extractVideoIds(message);
		List<String> titles = new ArrayList<>();
		for (String videoId : videoIds) {
			Optional<String> title = deArrowClient.findTitle(videoId);
			title.ifPresent(titles::add);
		}

		if (!titles.isEmpty()) {
			StringBuilder replyBuilder = new StringBuilder("DeArrow:\n");
			for (String title : titles) {
				replyBuilder.append(title).append("\n");
			}
			replyBuilder.append("\n").append("(Using https://sponsor.ajay.app)");
			return Optional.of(replyBuilder.toString());
		} else {
			return Optional.empty();
		}
	}
}
