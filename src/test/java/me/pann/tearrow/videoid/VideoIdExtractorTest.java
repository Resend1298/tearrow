package me.pann.tearrow.videoid;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class VideoIdExtractorTest {
	@ParameterizedTest
	@MethodSource("extractVideoIdsCases")
	void extractVideoIds(String input, List<String> expected) {
		List<String> actual = VideoIdExtractor.extractVideoIds(input);
		assertIterableEquals(expected, actual);
	}

	static Stream<Arguments> extractVideoIdsCases() {
		//noinspection SpellCheckingInspection,HttpUrlsUsage
		return Stream.of(
				Arguments.of(
						"https://www.youtube.com/watch?v=qWNQUvIk954",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"https://www.youtube.com/watch?v=qWNQUvIk954&t=44s",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"https://youtu.be/qWNQUvIk954",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"https://youtu.be/qWNQUvIk954?t=44",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"Check out this video: https://youtu.be/qWNQUvIk954, it's really good!",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"This text contains no URLs.",
						List.of()
				),
				Arguments.of(
						"Non-YouTube URL: https://www.example.com/watch?v=qWNQUvIk954",
						List.of()
				),
				Arguments.of(
						"Two videos: https://www.youtube.com/watch?v=qWNQUvIk954 and https://youtu.be/abc123def45",
						List.of("qWNQUvIk954", "abc123def45")
				),
				Arguments.of(
						"YouTube URL and non-YouTube URL: https://www.youtube.com/watch?v=qWNQUvIk954 and https://www.example.com/watch?v=abc123def45",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"Malformed URL: https://www.youtube.com/watch?v=, should be ignored.",
						List.of()
				),
				Arguments.of(
						"Malformed URL: https://www.youtube.com/watch, should be ignored.",
						List.of()
				),
				Arguments.of(
						"Malformed URL: https://youtu.be/, should be ignored.",
						List.of()
				),
				Arguments.of(
						"Malformed URL: https://youtu.be, should be ignored.",
						List.of()
				),
				Arguments.of(
						"URL next to punctuation: https://www.youtube.com/watch?v=qWNQUvIk954!",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"URL next to punctuation: (https://www.youtube.com/watch?v=qWNQUvIk954)",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"URL next to punctuation: https://www.youtube.com/watch?v=qWNQUvIk954。",
						List.of("qWNQUvIk954")
				),
				Arguments.of(
						"Not supported URL format: https://youtube.com/watch?v=qWNQUvIk954",
						List.of()
				),
				Arguments.of(
						"Not supported URL format: http://www.youtube.com/watch?v=qWNQUvIk954",
						List.of()
				),
				Arguments.of(
						"Not supported URL format: https://m.youtube.com/watch?v=qWNQUvIk954",
						List.of()
				)
		);
	}
}