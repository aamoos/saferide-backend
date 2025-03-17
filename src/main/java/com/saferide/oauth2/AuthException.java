package com.saferide.oauth2;

import com.saferide.error.CustomException;
import com.saferide.error.ErrorCode;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
