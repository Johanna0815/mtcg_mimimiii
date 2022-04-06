package com.mimimiii.battleTools.server;

public enum HttpStatus {

    OK(200, "OK"),
    CREATED(201, "CREATED"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SQL_ERROR(900,"SQL-Error");


// bug ---  int ^ -- no more final
    public  HttpStatus code;
    public  String message;

    public final int invalid;
    public final String ok;
    
    HttpStatus(HttpStatus code, String message, String ok) {
        this.code = code;
        this.message = message;
        this.ok = ok;
        invalid = 0;
    }

    HttpStatus(int invalid, String ok) {
        this.invalid = invalid;
        this.ok = ok;
    }
}
