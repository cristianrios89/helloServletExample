package com.greeninc.user.models.enums;

import lombok.Getter;

/**
 * Enumeration that contains all the possible generic errors that can be returned to a consumer.
 */
@Getter
public enum ErrorEnum {

    VALUE_NOT_FOUND(1, "TYPE_1"),
    ALREADY_EXISTS(2, "TYPE_2"),
    INTERNAL_SERVER_ERROR(3, "TYPE_3"),
    INVALID_FIELD_VALUE(4, "TYPE_4");

    private final int code;
    private final String type;

    /**
     * Constructor of the enumeration.
     *
     * @param code of the error.
     * @param type of the error.
     */
    ErrorEnum(final int code, final String type) {
        this.code = code;
        this.type = type;
    }

}
