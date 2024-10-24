package com.github.ngodat0103.yamp.ordersvc.exception;

import com.github.ngodat0103.yamp.ordersvc.exception.payload.ExceptionMsg;
import com.github.ngodat0103.yamp.ordersvc.exception.wrapper.CartNotFoundException;
import com.github.ngodat0103.yamp.ordersvc.exception.wrapper.OrderNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {

  @ExceptionHandler(
      value = {
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class,
      })
  public <T extends BindException> ResponseEntity<ExceptionMsg> handleValidationException(
      final T e) {

    log.info("**ApiExceptionHandler controller, handle validation exception*\n");
    final var badRequest = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(
        ExceptionMsg.builder()
            .msg("*" + e.getBindingResult().getFieldError().getDefaultMessage() + "!**")
            .httpStatus(badRequest)
            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
            .build(),
        badRequest);
  }

  @ExceptionHandler(
      value = {
        CartNotFoundException.class,
        OrderNotFoundException.class,
        IllegalStateException.class,
      })
  public <T extends RuntimeException> ResponseEntity<ExceptionMsg> handleApiRequestException(
      final T e) {

    log.info("**ApiExceptionHandler controller, handle API request*\n");
    final var badRequest = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(
        ExceptionMsg.builder()
            .msg("#### " + e.getMessage() + "! ####")
            .httpStatus(badRequest)
            .timestamp(ZonedDateTime.now(ZoneId.systemDefault()))
            .build(),
        badRequest);
  }
}