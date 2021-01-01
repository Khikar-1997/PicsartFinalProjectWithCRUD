package com.company.controller.model.user;

import com.company.persistance.model.user.Role;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRequestModel {
    private String name;
    private String surname;
    private Role role;
    private String email;
    private String username;
    private String password;

    public UserRequestModel(String name, String surname, Role role, String email, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
    }

    public UserRequestModel() {
    }

    public String mD5PasswordEncoder(String input) {
        MessageDigest digest = null;
        try {
            if (null == input)
                return null;

            digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());

        } catch (NoSuchAlgorithmException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        assert digest != null;
        return new BigInteger(1, digest.digest()).toString(16);
    }

    private boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("((?=.*\\d{3,})(?=.*[A-Z])(?=.*[#%!]).{8,40})");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isUsernameValid(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isEmailAddressValid(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isEmailAddressValid(email)) {
            this.email = email;
        } else {
            throw new RuntimeException("Email is not valid");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (isUsernameValid(username)) {
            this.username = username;
        } else {
            throw new RuntimeException("Username is mot valid");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (isPasswordValid(password)) {
            this.password = password;
        } else {
            throw new RuntimeException("Password is not valid");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequestModel that = (UserRequestModel) o;
        return Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && role == that.role && Objects.equals(email, that.email) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, role, email, username, password);
    }

    @Override
    public String toString() {
        return "UserRequestModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

