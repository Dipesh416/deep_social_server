package com.deep.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDetails {
	
	private String message;
	
	private String error;
	
	private LocalDateTime timeStamp;
}
