package com.eafit.herzon.teis.exceptions;

import com.eafit.herzon.teis.dto.ApiMessagesResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler class to handle exceptions globally.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles the MethodArgumentNotValidException exception.
   *
   * @param ex the exception to handle.
   * @return a ResponseEntity with the error message.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiMessagesResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex
  ) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .collect(Collectors.toList());

    return ResponseEntity.badRequest()
        .body(new ApiMessagesResponse(true, errors));
  }
}