package com.greeninc.user.exception;

import com.greeninc.user.models.enums.ErrorEnum;
import lombok.EqualsAndHashCode;

/**
 * Exception meant to be thrown when a bad request is received.
 */
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends ApiException {

    /**
     * Constructs a bad request type of exception.
     *
     * @param errorCode error code enumeration that represents the type of error.
     * @param msg       the message describing the error occurred.
     */
    public BadRequestException(final ErrorEnum errorCode, final String msg) {
        super(errorCode, msg);
    }

}
