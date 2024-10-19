package com.github.ngodat0103.yamp.productsvc.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @Getter
  static class Error {
    private final String detail;
    private final String pointer;
    @JsonIgnore private final String jsonPathSchema = "#/";

    public Error(String detail, String pointer) {
      this.detail = detail;
      this.pointer = jsonPathSchema + pointer;
    }
  }

  @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public ProblemDetail handleApiException(RuntimeException e, HttpServletRequest request) {
    ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.CONFLICT);
    problemDetails.setDetail(e.getMessage());
    problemDetails.setType(URI.create("https://problems-registry.smartbear.com/already-exists"));
    problemDetails.setTitle("Already exists");
    problemDetails.setDetail(e.getMessage());
    problemDetails.setInstance(URI.create(request.getServletPath()));
    return problemDetails;
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleMethodArgumentNotValidException(
      Exception e, HttpServletRequest request) {
    ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetails.setType(
        URI.create("https://problems-registry.smartbear.com/missing-body-property"));
    problemDetails.setDetail("The request is not valid.");
    problemDetails.setTitle("Validation Error.");
    problemDetails.setInstance(URI.create(request.getServletPath()));
    Set<Error> errors = new HashSet<>();
    if (e instanceof MethodArgumentNotValidException exception) {
      exception
          .getFieldErrors()
          .forEach(
              fieldError -> {
                Error error = new Error(fieldError.getDefaultMessage(), fieldError.getField());
                errors.add(error);
              });
    } else if (e instanceof HttpMessageNotReadableException exception) {
      Error error = new Error(exception.getMessage().split(":")[2], "UUID");
      errors.add(error);
    }
    problemDetails.setProperties(Collections.singletonMap("errors", errors));
    return problemDetails;
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
    ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetails.setType(URI.create("https://problems-registry.smartbear.com/bad-request"));
    problemDetails.setTitle("Unsupported Media Type");
    problemDetails.setDetail(e.getMessage());
    problemDetails.setInstance(URI.create(request.getServletPath()));
    return problemDetails;
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleNotFoundException(NotFoundException e, HttpServletRequest request) {
    ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problemDetails.setType(URI.create("https://problems-registry.smartbear.com/not-found"));
    problemDetails.setTitle("Not Found");
    problemDetails.setDetail(e.getMessage());
    problemDetails.setInstance(URI.create(request.getServletPath()));
    return problemDetails;
  }

  //    @ExceptionHandler(HttpMessageNotReadableException.class)
  //    @ResponseStatus(HttpStatus.BAD_REQUEST)
  //    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException
  // e, HttpServletRequest request) {
  //        ProblemDetail problemDetails = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
  //
  // problemDetails.setType(URI.create("https://problems-registry.smartbear.com/bad-request"));
  //        problemDetails.setTitle("Bad Request");
  //        problemDetails.setDetail(e.getMessage().split(":")[2]);
  //        problemDetails.setInstance(URI.create(request.getServletPath()));
  //        return problemDetails;
  //    }

}
