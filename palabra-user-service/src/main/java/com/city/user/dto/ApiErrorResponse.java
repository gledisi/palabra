package com.city.user.dto;

import com.city.user.exceptions.PalabraErrorStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrorResponse {

	private List<String> messages;
	private HttpStatus status;
	private String error;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime time = LocalDateTime.now();

	public ApiErrorResponse() {
		super();
		this.messages = new ArrayList<>();
	}

	public ApiErrorResponse(String message, HttpStatus status) {
		super();
		addStatusFields(status);
		this.messages = Arrays.asList(message);

	}

	public ApiErrorResponse(List<String> messages, HttpStatus status) {
		this.messages = messages;
		addStatusFields(status);
	}

	public ApiErrorResponse(PalabraErrorStatus errorStatus, List<String> messages) {
		this.error=errorStatus.getError();
		this.status=errorStatus.getStatus();
		this.messages = messages;
	}

	public ApiErrorResponse(HttpStatus httpStatus, List<String> messages) {
		this.status =httpStatus;
		this.messages = messages;
	}

	private void addStatusFields(HttpStatus status) {
		this.status = status;
		this.error = status.getReasonPhrase();
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getTime() {
		return time;
	}

}
