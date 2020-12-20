package com.flab.makedel.exception;

import java.sql.SQLException;

public class WrongDataSourceException extends SQLException {

    public WrongDataSourceException(String message) {
        super(message);
    }
}
