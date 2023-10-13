package ru.batorov.testTask.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import ru.batorov.testTask.services.UniqueSymbolsService;
import ru.batorov.testTask.utils.ErrorResponse;
import ru.batorov.testTask.utils.exceptions.StringLengthOutOfBoundException;

/**
 * RestController with operations that return the frequency of characters
 */
@RestController
@Tag(name = "UniqueSymbols", description = "Contains operations that return the frequency of characters")
public class UniqueSymbolsController {

	private final UniqueSymbolsService uniqueSymbolsService;
	private final int maxLength = (int) Math.pow(10, 3);

	public UniqueSymbolsController(UniqueSymbolsService uniqueSymbolsService) {
		this.uniqueSymbolsService = uniqueSymbolsService;
	}

	@PostMapping("/string")
	@Operation(summary = "Return a string with the frequency of characters", description = "Return a string with the sorted frequency of characters on the specified string in descending order", tags = "UniqueSymbols")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "415", description = "String must be valid"),
		@ApiResponse(responseCode = "422", description = "String's length must be valid")
	})
	public String stingOfUniqueSymbols(@RequestBody() @Parameter(name = "String", description = "Original string(Max length: 1000)") String str) {// 
		if (maxLength <= str.length())
			throw new StringLengthOutOfBoundException();

		return uniqueSymbolsService.stringOfUniqueSymbols(str);
	}

	@PostMapping("/map")
	@Operation(summary = "Return a map with the frequency of characters", description = "Return a string with the sorted frequency of characters on the specified string in descending order", tags = "UniqueSymbols")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "415", description = "String must be valid"),
		@ApiResponse(responseCode = "422", description = "String's length must be valid")
	})
	public Map<Character, Integer> mapOfUniqueSymbols(@RequestBody @Parameter(name = "String", description = "Original string(Max length: 1000)") String str) {
		if (maxLength < str.length())
			throw new StringLengthOutOfBoundException();

		return uniqueSymbolsService.mapOfUniqueSymbols(str);
	}

	/**
	 * ExceptionHandler for HttpMessageNotReadableException
	 * 
	 * @param exc HttpMessageNotReadableException
	 * @return description of the problem
	 */
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ResponseEntity<ErrorResponse> handlerException(HttpMessageNotReadableException exc) {
		ErrorResponse errorResponse = new ErrorResponse(exc.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);// 415
	}

	/**
	 * ExceptionHandler for StringLengthOutOfBoundException
	 * @param exc StringLengthOutOfBoundException
	 * @return description of the problem
	 */
	@ExceptionHandler({ StringLengthOutOfBoundException.class })
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<ErrorResponse> handlerException(StringLengthOutOfBoundException exc) {
		ErrorResponse errorResponse = new ErrorResponse(exc.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);// 422
	}
}
