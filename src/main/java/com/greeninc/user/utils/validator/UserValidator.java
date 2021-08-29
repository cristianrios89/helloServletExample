package com.greeninc.user.utils.validator;

import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.enums.ErrorEnum;
import org.apache.commons.lang3.StringUtils;

import static com.greeninc.user.models.constants.ApiConstants.FIRST_NAME_NOT_BLANK;
import static com.greeninc.user.models.constants.ApiConstants.LAST_NAME_NOT_BLANK;
import static com.greeninc.user.models.constants.ApiConstants.USER_DATA_NOT_NULL;
import static com.greeninc.user.models.constants.ApiConstants.USER_ID_NOT_NULL;

/**
 * Validator class that validates user request DTOs.
 */
public class UserValidator {

    private UserValidator() {

    }

    /**
     * Validates a {@link UserRqDTO} on creation.
     *
     * @param user to be validated
     * @throws BadRequestException when the data is invalid.
     */
    public static void validateUserOnCreation(final UserRqDTO user) throws BadRequestException {
        objectNotNull(user, USER_DATA_NOT_NULL);
        stringNotBlank(user.getFirstName(), FIRST_NAME_NOT_BLANK);
        stringNotBlank(user.getLastName(), LAST_NAME_NOT_BLANK);
    }

    /**
     * Validates a user id on retrieve.
     *
     * @param userId to be validated
     * @throws BadRequestException when the data is invalid.
     */
    public static void validateUserOnRetrieve(final Long userId) throws BadRequestException {
        objectNotNull(userId, USER_ID_NOT_NULL);
    }

    /**
     * Validates a {@link UserRqDTO} and user id on update.
     *
     * @param userId to be validated.
     * @param user   to be validated.
     * @throws BadRequestException when the data is invalid.
     */
    public static void validateUserOnUpdate(final Long userId, final UserRqDTO user) throws BadRequestException {
        objectNotNull(userId, USER_ID_NOT_NULL);
        validateUserOnCreation(user);
    }

    /**
     * Validates a user id on delete.
     *
     * @param userId to be validated
     */
    public static void validateUserOnDelete(final Long userId) throws BadRequestException {
        validateUserOnRetrieve(userId);
    }

    private static void objectNotNull(final Object field, final String msg) throws BadRequestException {
        if (field == null) {
            throw new BadRequestException(ErrorEnum.INVALID_FIELD_VALUE, msg);
        }
    }

    private static void stringNotBlank(final String field, final String msg) throws BadRequestException {
        if (StringUtils.isBlank(field)) {
            throw new BadRequestException(ErrorEnum.INVALID_FIELD_VALUE, msg);
        }
    }

}
