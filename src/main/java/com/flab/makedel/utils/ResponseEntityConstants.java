package com.flab.makedel.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityConstants {

    public static final ResponseEntity<Void> RESPONSE_OK = new ResponseEntity(HttpStatus.OK);
    public static final ResponseEntity<Void> RESPONSE_CONFLICT = new ResponseEntity(
        HttpStatus.CONFLICT);
    public static final ResponseEntity<Void> RESPONSE_NOT_FOUND = new ResponseEntity(
        HttpStatus.NOT_FOUND);
}

