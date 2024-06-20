package com.deep.exception;

import lombok.experimental.SuperBuilder;

public class ChatException extends Exception{

	public ChatException(String message) {
		super(message);
	}
}
