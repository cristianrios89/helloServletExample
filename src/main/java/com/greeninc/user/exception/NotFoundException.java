package com.greeninc.user.exception;

import com.greeninc.user.models.enums.ErrorEnum;
import lombok.EqualsAndHashCode;

/**
 * Exception meant to be thrown when a value can not be found.
 */
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends ApiException {

    /**
     * Constructs a not found type of exception.
     *
     * @param errorCode error code enumeration that represents the type of error.
     * @param msg       the message describing the error occurred.
     */
    public NotFoundException(final ErrorEnum errorCode, final String msg) {
        super(errorCode, msg);
    }

}
