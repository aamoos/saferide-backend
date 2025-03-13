package com.saferide.error;

public record ErrorResponse(
        ErrorCode errorCode,
        String message
) {

}
