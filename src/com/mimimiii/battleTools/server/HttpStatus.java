package com.mimimiii.battleTools.server;

public enum HttpStatus {

    OK(200, "OK"),
    CREATED(201, "CREATED"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SQL_ERROR(900,"SQL-Error");

    public final HttpStatus code;
    public final String message;

    HttpStatus(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

}
