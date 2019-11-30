package com.devglan.handler;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devglan.io.ExceptionResponseModel;
import com.devglan.io.StatusMessage;



/**
 * This class used to define global and custom exceptions for server internal error, custom, bad request, time out etc,.
 * @author Sasikumar Chinnamuthu
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger log = Logger.getLogger(GlobalExceptionHandler.class.getSimpleName());
	
	/**
	 * This method used to throw server internal error at global level
	 * @author Sasikumar Chinnamuthu
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseModel> generalException(Exception e) throws Exception{
		log.info("Exception type Exception in generalException() in  class GlobalExceptionHandler and exception is " +  e);
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus("INTERNAL_SERVER_ERROR");
		model.setStatusMessage(new StatusMessage("INTERNAL_SERVER_ERROR_MSG", "Internal Server Error. Please try again later."));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * This method used to send bad request response error at global level
	 * @author Sasikumar Chinnamuthu
	 * @param e
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponseModel> handleIllegalArgumentException(IllegalArgumentException e) throws IOException {
		log.info("Exception type IllegalArgumentException in handleIllegalArgumentException() in  class GlobalExceptionHandler and exception is " +  e);
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus("BAD_REQUEST");
		model.setStatusMessage(new StatusMessage("BAD_REQUEST_MSG", e.getMessage()));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.BAD_REQUEST);
	}
	
	
		
}
