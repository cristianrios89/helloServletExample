package com.greeninc.user.controller;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.exception.NotFoundException;
import com.greeninc.user.models.constants.ApiConstants;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.ErrorRsDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.enums.ErrorEnum;
import com.greeninc.user.service.UsersService;
import com.greeninc.user.utils.CustomMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.greeninc.user.testutils.GenericTestData.USER_ID;
import static com.greeninc.user.testutils.GenericTestData.createErrorResponse;
import static com.greeninc.user.testutils.GenericTestData.createUserRequest;
import static com.greeninc.user.testutils.GenericTestData.createUserResponse;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mockMvc;

    // Creation tests

    @Test
    void whenCreateUserThenOk() throws Exception {
        final UserRqDTO userToCreate = createUserRequest();
        final UserRsDTO createdUser = createUserResponse(userToCreate);
        when(this.usersService.createUser(userToCreate)).thenReturn(createdUser);
        this.mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomMapper.serialize(userToCreate).orElse("{}"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(CustomMapper.serialize(createdUser).orElse("{}")));
    }

    @Test
    void whenCreateUserThenAlreadyExists() throws Exception {
        final UserRqDTO userToCreate = createUserRequest();
        final ApiException exceptionToThrow = new BadRequestException(ErrorEnum.ALREADY_EXISTS, ApiConstants.USER_ALREADY_EXISTS);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.createUser(userToCreate)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomMapper.serialize(userToCreate).orElse("{}"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    @Test
    void whenCreateUserThenBadRequest() throws Exception {
        final UserRqDTO userToCreate = createUserRequest();
        final ApiException exceptionToThrow = new BadRequestException(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_DATA_NOT_NULL);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.createUser(userToCreate)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomMapper.serialize(userToCreate).orElse("{}"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    // Retrieval tests

    @Test
    void whenRetrieveUserThenOk() throws Exception {
        final UserRsDTO userToRetrieve = createUserResponse();
        when(this.usersService.retrieveUser(USER_ID)).thenReturn(userToRetrieve);
        this.mockMvc
                .perform(get("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(CustomMapper.serialize(userToRetrieve).orElse("{}")));
    }

    @Test
    void whenRetrieveUserThenNotFound() throws Exception {
        final ApiException exceptionToThrow = new NotFoundException(ErrorEnum.VALUE_NOT_FOUND, ApiConstants.USER_NOT_FOUND_MSG);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.retrieveUser(USER_ID)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(get("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    @Test
    void whenRetrieveUserThenBadRequest() throws Exception {
        final BadRequestException exceptionToThrow = new BadRequestException(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_ID_NOT_NULL);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.retrieveUser(USER_ID)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(get("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    @Test
    void whenRetrieveUserThenThrowApiException() throws Exception {
        final ApiException exceptionToThrow = new ApiException(ErrorEnum.INTERNAL_SERVER_ERROR, "Unexpected error");
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.retrieveUser(USER_ID)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(get("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    @Test
    void whenRetrieveUserThenThrowException() throws Exception {
        final Exception exceptionToThrow = new RuntimeException("Unexpected error");
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.retrieveUser(USER_ID)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(get("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    // Update tests

    @Test
    void whenUpdateUserThenOk() throws Exception {
        final String newPhoneNumber = "1112233";
        final UserRqDTO userDataToSet = createUserRequest(newPhoneNumber);
        final UserRsDTO userToRetrieve = createUserResponse(userDataToSet, newPhoneNumber);
        when(this.usersService.updateUser(USER_ID, userDataToSet)).thenReturn(userToRetrieve);
        this.mockMvc
                .perform(put("/users/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomMapper.serialize(userDataToSet).orElse("{}"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(CustomMapper.serialize(userToRetrieve).orElse("{}")));
    }

    @Test
    void whenUpdateUserThenNotFound() throws Exception {
        final String newPhoneNumber = "1112233";
        final UserRqDTO userDataToSet = createUserRequest(newPhoneNumber);
        final ApiException exceptionToThrow = new NotFoundException(ErrorEnum.VALUE_NOT_FOUND, ApiConstants.USER_NOT_FOUND_MSG);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.updateUser(USER_ID, userDataToSet)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(put("/users/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomMapper.serialize(userDataToSet).orElse("{}"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    @Test
    void whenUpdateUserThenBadRequest() throws Exception {
        final BadRequestException exceptionToThrow = new BadRequestException(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_DATA_NOT_NULL);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        when(this.usersService.updateUser(USER_ID, null)).thenThrow(exceptionToThrow);
        this.mockMvc
                .perform(put("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    // Delete tests

    @Test
    void whenDeleteUserThenOk() throws Exception {
        doNothing().when(this.usersService).deleteUser(USER_ID);
        this.mockMvc
                .perform(delete("/users/" + USER_ID))
                .andExpect(status().isOk());
        verify(this.usersService, atLeastOnce()).deleteUser(USER_ID);
    }

    @Test
    void whenDeleteUserThenNotFound() throws Exception {
        final ApiException exceptionToThrow = new NotFoundException(ErrorEnum.VALUE_NOT_FOUND, ApiConstants.USER_NOT_FOUND_MSG);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        doThrow(exceptionToThrow).when(this.usersService).deleteUser(USER_ID);
        this.mockMvc
                .perform(delete("/users/" + USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

    @Test
    void whenDeleteUserThenBadRequest() throws Exception {
        final BadRequestException exceptionToThrow = new BadRequestException(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_ID_NOT_NULL);
        final ErrorRsDTO errorToExpect = createErrorResponse(exceptionToThrow);
        doThrow(exceptionToThrow).when(this.usersService).deleteUser(USER_ID);
        this.mockMvc
                .perform(delete("/users/" + USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(CustomMapper.serialize(errorToExpect).orElse("{}")));
    }

}