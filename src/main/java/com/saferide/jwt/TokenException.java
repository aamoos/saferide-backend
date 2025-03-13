package com.saferide.jwt;

import com.saferide.error.CustomException;
import com.saferide.error.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
