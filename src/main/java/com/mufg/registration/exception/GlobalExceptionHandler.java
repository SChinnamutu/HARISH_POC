package com.mufg.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mufg.registration.io.StatusMessage;

import lombok.extern.slf4j.Slf4j;


/**
 * This class used to define global and custom exceptions for server internal error, custom, bad request, time out etc,.
 * @author Sasikumar Chinnamuthu
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * This method used to throw server internal error at global level
	 * @author Sasikumar Chinnamuthu
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseModel> generalException(Exception e) throws Exception{
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus("INTERNAL_SERVER_ERROR");
		model.setStatusMessage(new StatusMessage("INTERNAL_SERVER_ERROR_MSG", "Internal Server Error. Please try again later."));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
