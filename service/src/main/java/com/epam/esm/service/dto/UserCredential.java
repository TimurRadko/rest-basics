package com.epam.esm.service.dto;

import java.util.Objects;

public class UserCredential {
  private String login;
  private String password;
  private String confirmPassword;

  public UserCredential() {}

  public UserCredential(String login, String password, String confirmPassword) {
    this.login = login;
    this.password = password;
    this.confirmPassword = confirmPassword;
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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserCredential that = (UserCredential) o;

    if (!Objects.equals(login, that.login)) {
      return false;
    }
    if (!Objects.equals(password, that.password)) {
      return false;
    }
    return Objects.equals(confirmPassword, that.confirmPassword);
  }

  @Override
  public int hashCode() {
    int result = login != null ? login.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UsersCreatingDto{"
        + "login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + ", confirmPassword='"
        + confirmPassword
        + '\''
        + '}';
  }
}
