package com.epam.esm.security;

import java.util.Objects;

public class LoginAndPasswordAuthenticationRequest {
  private String login;
  private String password;

  public LoginAndPasswordAuthenticationRequest() {}

  public LoginAndPasswordAuthenticationRequest(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LoginAndPasswordAuthenticationRequest that = (LoginAndPasswordAuthenticationRequest) o;

    if (!Objects.equals(login, that.login)) {
      return false;
    }
    return Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    int result = login != null ? login.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "LoginAndPasswordAuthenticationRequest{"
        + "login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
