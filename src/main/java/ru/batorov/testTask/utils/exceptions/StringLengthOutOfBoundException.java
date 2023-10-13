package ru.batorov.testTask.utils.exceptions;

/**
 * Thrown when the given string's length is too large
 */
public class StringLengthOutOfBoundException extends RuntimeException {
	public StringLengthOutOfBoundException(){
		super("Given string is too large!");
	}
}
