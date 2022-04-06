package com.mimimiii.battleTools.user;

import java.util.Objects;

public class Token {


    private String username;
    private String token;

    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }


    public Token() {
        this.token = "";
        this.username = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }


    //probar
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(username, token1.username) && Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token);
    }


}
