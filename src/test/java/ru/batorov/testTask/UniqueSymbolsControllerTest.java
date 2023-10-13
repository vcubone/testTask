package ru.batorov.testTask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.batorov.testTask.controllers.UniqueSymbolsController;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class with tests of UniqueSymbolsController
 */
@SpringBootTest
@AutoConfigureMockMvc
class UniqueSymbolsControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private UniqueSymbolsController uniqueSymbolsController;
	@Autowired
	private ObjectMapper objectMapper;
	private String strURL = "/string";
	private String mapURL = "/map";
	private int maxLength = (int) Math.pow(10, 6);

	public UniqueSymbolsControllerTest() {
	}

	@Test
	public void controllerTest() {
		assertNotNull(uniqueSymbolsController, "no UniqueSymbolsController");
	}

	@Test
	public void invalidDataTest() throws Exception {
		mvc.perform(post(strURL)).andDo(print()).andExpect(status().isUnsupportedMediaType());
		mvc.perform(post(strURL).contentType(
				MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnsupportedMediaType());
		mvc.perform(post(strURL)
				.content(""))
				.andDo(print()).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void lengthOutOfBound() throws Exception {
		char[] array = new char[maxLength];
		Arrays.fill(array, 'a');
		String str = new String(array);
		mvc.perform(post(strURL).content(str)).andDo(print()).andExpect(status().isUnprocessableEntity());

		array = new char[maxLength + 1];
		Arrays.fill(array, 'a');
		str = new String(array);
		mvc.perform(post(strURL).content(str)).andDo(print()).andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void longValid() throws Exception {
		int amountOfChars = maxLength - 1;
		char[] array = new char[amountOfChars];
		Map<Character, Integer> expected = new LinkedHashMap<>();
		expected.put('a', amountOfChars);
		Arrays.fill(array, 'a');
		String str = new String(array);
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();

		Arrays.fill(array, amountOfChars / 2, array.length, 'z');
		str = new String(array);
		expected.put('z', amountOfChars / 2 + 1);
		expected.put('a', amountOfChars / 2);
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();

		
		amountOfChars = maxLength - 2;
		array = new char[amountOfChars];
		Arrays.fill(array, 'a');
		expected.put('a', amountOfChars);
		str = new String(array);
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();

		Arrays.fill(array, amountOfChars / 2 - 1, array.length, 'z');
		str = new String(array);
		expected.put('z', amountOfChars / 2 + 1);
		expected.put('a', amountOfChars / 2 - 1);
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();
		
		
		amountOfChars = maxLength / 2;
		array = new char[amountOfChars];
		Arrays.fill(array, 'a');
		expected.put('a', amountOfChars);
		str = new String(array);
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();

		Arrays.fill(array, amountOfChars / 2 - 1, array.length, 'z');
		str = new String(array);
		expected.put('z', amountOfChars / 2 + 1);
		expected.put('a', amountOfChars / 2 - 1);
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();
	}
	
	@Test
	public void shortValid() throws Exception {
		Map<Character, Integer> expected = new LinkedHashMap<>();
		expected.put('a', 1);
		String str = "a";
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();
		
		expected.put('a', 2);
		expected.put('b', 1);
		str = "aab";
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();
		
		
		expected.put('a', 2);
		expected.put('b', 1);
		str = "aab";
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();
		
		expected.put('d', 8);
		expected.put('c', 6);
		expected.put('b', 4);
		expected.put('a', 2);
		expected.put(';', 1);
		str = "abbcccddddddddcccbba;";
		strMvcPerform(strURL, str, expected);
		mapMvcPerform(mapURL, str, expected);
		expected.clear();
	}

	private void strMvcPerform(String url, String content, Map<Character, Integer> expected) throws Exception{
		mvc.perform(post(url).content(content)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(mapToString(expected)));
	}
	
	private void mapMvcPerform(String url, String content, Map<Character, Integer> expected) throws Exception{
		mvc.perform(post(url).content(content)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(expected)));
	}

	private String mapToString(Map<Character, Integer> unsortedMap) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<Character, Integer> entry : unsortedMap.entrySet()) {
			stringBuilder.append("\"" + entry.getKey() + "\": " + entry.getValue() + ", ");
		}
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		return stringBuilder.toString();
	}
}
