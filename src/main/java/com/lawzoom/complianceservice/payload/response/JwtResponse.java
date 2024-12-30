package com.lawzoom.complianceservice.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
  private Boolean isAssociated;

  private Boolean subscribed;

  public JwtResponse(String token, Long id, String username, String email, List<String> roles, Boolean isAssociated,Boolean subscribed) {

    this.token=token;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.isAssociated = isAssociated;
    this.subscribed =subscribed;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public Boolean getAssociated() {
    return isAssociated;
  }

  public void setAssociated(Boolean associated) {
    isAssociated = associated;
  }

  public Boolean getSubscribed() {
    return subscribed;
  }

  public void setSubscribed(Boolean subscribed) {
    this.subscribed = subscribed;
  }
}