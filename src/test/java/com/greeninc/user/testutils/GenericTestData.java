package com.greeninc.user.testutils;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.ErrorRsDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.entity.User;
import com.greeninc.user.models.enums.ErrorEnum;

public class GenericTestData {

    public static final Long USER_ID = 1L;

    private GenericTestData() {

    }

    public static UserRqDTO createUserRequest() {
        return createUserRequest("4445566");
    }

    public static UserRqDTO createUserRequest(final String phone) {
        return UserRqDTO.builder().firstName("pepe").lastName("argento").email("pepe.argento@mail.com").phone(phone).build();
    }

    public static UserRsDTO createUserResponse() {
        return createUserResponse(createUserRequest());
    }

    public static UserRsDTO createUserResponse(final UserRqDTO dataToSet) {
        return createUserResponse(dataToSet, "4445566");
    }

    public static UserRsDTO createUserResponse(final UserRqDTO dataToSet, final String phone) {
        return UserRsDTO.builder().id(USER_ID).firstName(dataToSet.getFirstName()).lastName(dataToSet.getLastName()).email(dataToSet.getEmail()).phone(phone).build();
    }

    public static UserRsDTO createUserResponse(final User dataToSet) {
        return UserRsDTO.builder().id(dataToSet.getId()).firstName(dataToSet.getFirstName()).lastName(dataToSet.getLastName()).email(dataToSet.getEmail()).phone(dataToSet.getPhone()).build();
    }

    public static ErrorRsDTO createErrorResponse(final Exception exception) {
        return new ErrorRsDTO(ErrorEnum.INTERNAL_SERVER_ERROR.getCode(), ErrorEnum.INTERNAL_SERVER_ERROR.getType(), exception.getMessage());
    }

    public static ErrorRsDTO createErrorResponse(final ApiException exception) {
        return new ErrorRsDTO(exception.getCode(), exception.getType(), exception.getMessage());
    }

    public static User createPreSaveUserEntity() {
        return createPreSaveUserEntity(createUserRequest());
    }

    public static User createPreSaveUserEntity(final UserRqDTO dataToSet) {
        return createPreSaveUserEntity(dataToSet, "4445566");
    }

    public static User createPreSaveUserEntity(final UserRqDTO dataToSet, final String phone) {
        return User.builder().firstName(dataToSet.getFirstName()).lastName(dataToSet.getLastName()).email(dataToSet.getEmail()).phone(phone).build();
    }

    public static User createPostSaveUserEntity() {
        return createPostSaveUserEntity(createPreSaveUserEntity());
    }

    public static User createPostSaveUserEntity(final User dataToSet) {
        return User.builder().id(USER_ID).firstName(dataToSet.getFirstName()).lastName(dataToSet.getLastName()).email(dataToSet.getEmail()).phone(dataToSet.getPhone()).build();
    }

}
