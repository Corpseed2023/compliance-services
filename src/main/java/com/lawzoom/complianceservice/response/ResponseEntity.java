package com.lawzoom.complianceservice.response;

import ch.qos.logback.core.joran.action.Action;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatus;


import java.util.Date;

public class ResponseEntity<T> {

  private static HttpStatus status;
  private int statusCode;
  private String message;
  private T body;

  private Date currentTimeStamp=new Date();

  public static ResponseEntity notFound() {
    return null;
  }

  public static Action status(HttpStatus httpStatus) {
    return null;
  }

  public static ResponseEntity creationComplete(String complianceSuccessfullyCreate, HttpStatus httpStatus) {
    return null;
  }

  public static ResponseEntity failedStatus(String failedToCreateCompliance, HttpStatus httpStatus) {
    return null;
  }

  public static ResponseEntity<ComplianceResponse> updatedStatus(String s) {

    return null;
  }

    public static ResponseEntity<ComplianceResponse> fetchDataStatus(String s) {
      return null;
    }


    public ResponseEntity fail(T body, String errMsg) {
    setStatus(HttpStatus.NOT_FOUND);
    setStatusCode(HttpStatus.NOT_FOUND.value());
    setMessage(errMsg);
    setBody(body);
    return this;
  }

  public ResponseEntity getFail(Object errMsg, HttpStatus status) {
    setStatus(status);
    setStatusCode(status.value());
    return this;
  }

  public ResponseEntity internalServerError() {
    setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    setMessage("Something went wrong, Please try-again later !!");
    setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return this;
  }


  public static ResponseEntity ok() {
//    setMessage("success");
//    setStatus(HttpStatus.OK);
//    setStatusCode(HttpStatus.OK.value());
//    return this;
    return null;
  }

  public ResponseEntity ok(T body) {

    return null;
  }

  public ResponseEntity ok(T body, String msg) {
    setBody(body);
    setStatus(HttpStatus.OK);
    setStatusCode(HttpStatus.OK.value());
    setMessage(msg);
    return this;
  }

  public ResponseEntity ok(String msg) {
    setBody(null);
    setStatus(HttpStatus.OK);
    setStatusCode(HttpStatus.OK.value());
    setMessage(msg);
    return this;
  }

  public ResponseEntity noContent() {
    setBody(null);
    setStatus(HttpStatus.NO_CONTENT);
    setStatusCode(HttpStatus.NO_CONTENT.value());
    setMessage("No Content");
    return this;
  }

  public ResponseEntity badRequest(String message) {
    setBody(null);
    setStatus(HttpStatus.BAD_REQUEST);
    setStatusCode(HttpStatus.BAD_REQUEST.value());
    setMessage(message);
    return this;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public ResponseEntity<T> setStatus(HttpStatus status) {
    this.status = status;
    this.statusCode = status.value();
    return this;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public ResponseEntity<T> setStatusCode(int statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public ResponseEntity<T> setMessage(String message) {
    this.message = message;
    return this;
  }

  public T getBody() {
    return body;
  }

  public ResponseEntity<T> setBody(T body) {
    this.body = body;
    return this;
  }

  public Date getCurrentTimeStamp() {
    return currentTimeStamp;
  }

  public void setCurrentTimeStamp(Date currentTimeStamp) {
    this.currentTimeStamp = currentTimeStamp;
  }


    public ResponseEntity<ComplianceResponse> build() {
      return null;
    }

    public ResponseEntity successfullStatus(String s) {
      return null;
    }
}
