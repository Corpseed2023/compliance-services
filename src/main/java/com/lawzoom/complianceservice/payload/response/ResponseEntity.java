package com.lawzoom.complianceservice.payload.response;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ResponseEntity<T> {

  private static HttpStatus status;
  private int statusCode;
  private String message;
  private T body;

  private Date currentTimeStamp=new Date();


    public ResponseEntity() {

    }


  public ResponseEntity notFound(Class entity) {
    setMessage(entity.getSimpleName() + " not Found");
    setStatus(HttpStatus.NOT_FOUND);
    setStatusCode(HttpStatus.NOT_FOUND.value());
    return this;
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

  // Constructor to initialize fields
  public ResponseEntity(T body, HttpStatus status) {
    this.status = status;
    this.statusCode = status.value();
    this.body = body;
    this.message = "Success"; // Default message
    this.currentTimeStamp = new Date();
  }

  public ResponseEntity ok() {
    setMessage("success");
    setStatus(HttpStatus.OK);
    setStatusCode(HttpStatus.OK.value());
    return this;
  }

  public ResponseEntity ok(T body) {
    setStatus(HttpStatus.OK);
    setMessage("Success");
    setStatusCode(HttpStatus.OK.value());
    setBody(body);
    return this;
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

  public ResponseEntity noContent(String privilegeNotFound) {
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
}
