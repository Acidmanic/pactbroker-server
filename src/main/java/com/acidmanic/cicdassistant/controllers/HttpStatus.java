/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

/**
 *
 * @author diego
 */
public class HttpStatus {
    
    public static final HttpStatus NOT_FOUND = new HttpStatus("Not found", 404);
    public static final HttpStatus UNAUTHORIZED = new HttpStatus("Un Authorized", 401);
    public static final HttpStatus BAD_REQUEST = new HttpStatus("Bad Request", 400);
    public static final HttpStatus INTERNAL_SERVER_ERROR = new HttpStatus("Internal Server Error", 500);
    public static final HttpStatus OK = new HttpStatus("Ok", 200);
    
    
    private String message;
    private int code;

    public HttpStatus(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public HttpStatus() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    
}
