package ru.batorov.testTask.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

/**
 * Service class with operations that return amount of unique characters
 * in a string
 */
@Service
public class UniqueSymbolsService {
	/**
	 * Return a string with the sorted frequency of characters on the specified
	 * string in descending order.
	 * 
	 * @param str a string.
	 * @return a string of the the frequency of characters
	 */
	public String stringOfUniqueSymbols(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		sortedStream(buildMap(str)).forEach((a) -> {
			stringBuilder.append("\"" + a.getKey() + "\": " + a.getValue() + ", ");
		});
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		return stringBuilder.toString();
	}

	/**
	 * Return a map with the frequency of characters on the
	 * specified string in descending order.
	 * 
	 * @param str a string.
	 * @return a map with the the frequency of characters
	 */
	public Map<Character, Integer> mapOfUniqueSymbols(String str) {
		return sortedStream(buildMap(str))
				.collect(Collectors.toMap(
						entryset -> entryset.getKey(),
						entryset -> entryset.getValue(),
						(one, two) -> (one), // useless due to map->map
						LinkedHashMap::new));
	}

	/**
	 * Return a stream of entrysets sorted by value in descending order
	 * 
	 * @param map with the frequency of characters
	 * @return sorted stream
	 */
	private Stream<Entry<Character, Integer>> sortedStream(Map<Character, Integer> map) {
		return map.entrySet().stream()
				.sorted((a, b) -> (b.getValue() - a.getValue()));
	}

	/**
	 * Return a map with the frequency of characters
	 * 
	 * @param str a string
	 * @return a map
	 */
	private Map<Character, Integer> buildMap(String str) {
		Map<Character, Integer> map = new HashMap<>();
		for (char i : str.toCharArray()) {
			map.merge(i, 1, Integer::sum);
		}
		return map;
	}
}
