package com.greeninc.user.exception;

import com.greeninc.user.models.enums.ErrorEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Generic exception to be thrown when no specific one exists.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class ApiException extends Exception {

    private final int code;
    private final String type;
    private final String message;

    /**
     * Constructs a generic type of exception.
     *
     * @param errorCode error code enumeration that represents the type of error.
     * @param msg       the message describing the error occurred.
     */
    public ApiException(final ErrorEnum errorCode, final String msg) {
        super(msg);
        this.code = errorCode.getCode();
        this.type = errorCode.getType();
        this.message = msg;
    }

}
