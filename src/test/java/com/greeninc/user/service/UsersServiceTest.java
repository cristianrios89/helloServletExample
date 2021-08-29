package com.greeninc.user.service;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.models.constants.ApiConstants;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.entity.User;
import com.greeninc.user.models.enums.ErrorEnum;
import com.greeninc.user.repository.UsersRepository;
import com.greeninc.user.service.impl.UsersServiceImpl;
import com.greeninc.user.utils.converter.UserRqConverter;
import com.greeninc.user.utils.converter.UserRsConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.greeninc.user.testutils.GenericTestComparators.objectsAreEqual;
import static com.greeninc.user.testutils.GenericTestData.USER_ID;
import static com.greeninc.user.testutils.GenericTestData.createPostSaveUserEntity;
import static com.greeninc.user.testutils.GenericTestData.createPreSaveUserEntity;
import static com.greeninc.user.testutils.GenericTestData.createUserRequest;
import static com.greeninc.user.testutils.GenericTestData.createUserResponse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UserRqConverter userRqConverter;
    @Mock
    private UserRsConverter userRsConverter;
    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void whenCreateThenOk() {
        final UserRqDTO userToCreate = createUserRequest();
        when(this.usersRepository.existsByFirstNameAndLastName(userToCreate.getFirstName(), userToCreate.getLastName())).thenReturn(false);
        final User preSavedUser = createPreSaveUserEntity(userToCreate);
        when(this.userRqConverter.convert(userToCreate)).thenReturn(preSavedUser);
        final User postSavedUser = createPostSaveUserEntity(preSavedUser);
        when(this.usersRepository.save(preSavedUser)).thenReturn(postSavedUser);
        final UserRsDTO savedUser = createUserResponse(postSavedUser);
        when(this.userRsConverter.convert(postSavedUser)).thenReturn(savedUser);
        try {
            final UserRsDTO createdUser = this.usersService.createUser(userToCreate);
            objectsAreEqual(userToCreate, createdUser);
        } catch (BadRequestException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserAlreadyExistsWhenCreateThenAlreadyExists() {
        final UserRqDTO userToCreate = createUserRequest();
        when(this.usersRepository.existsByFirstNameAndLastName(userToCreate.getFirstName(), userToCreate.getLastName())).thenReturn(true);
        try {
            this.usersService.createUser(userToCreate);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.ALREADY_EXISTS, ApiConstants.USER_ALREADY_EXISTS, e);
        }
    }

    @Test
    void givenDataIsNullWhenCreateThenBadRequest() {
        try {
            this.usersService.createUser(null);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_DATA_NOT_NULL, e);
        }
    }

    @Test
    void whenRetrieveThenOk() {
        final User postSavedUser = createPostSaveUserEntity();
        when(this.usersRepository.findById(USER_ID)).thenReturn(Optional.of(postSavedUser));
        final UserRsDTO savedUser = createUserResponse(postSavedUser);
        when(this.userRsConverter.convert(postSavedUser)).thenReturn(savedUser);
        try {
            final UserRsDTO retrievedUser = this.usersService.retrieveUser(USER_ID);
            objectsAreEqual(postSavedUser, retrievedUser);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserNotExistsWhenRetrieveThenNotFound() {
        when(this.usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        try {
            this.usersService.retrieveUser(USER_ID);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.VALUE_NOT_FOUND, ApiConstants.USER_NOT_FOUND_MSG, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserNotExistsWhenRetrieveThenBadRequest() {
        try {
            this.usersService.retrieveUser(null);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_ID_NOT_NULL, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void whenUpdateThenOk() {
        final User preUpdatedUser = createPostSaveUserEntity();
        when(this.usersRepository.findById(USER_ID)).thenReturn(Optional.of(preUpdatedUser));
        final User postUpdatedUser = createPostSaveUserEntity();
        final UserRqDTO userNewData = createUserRequest("1112233");
        postUpdatedUser.setPhone(userNewData.getPhone());
        when(this.usersRepository.save(any(User.class))).thenReturn(postUpdatedUser);
        final UserRsDTO savedUser = createUserResponse(postUpdatedUser);
        when(this.userRsConverter.convert(postUpdatedUser)).thenReturn(savedUser);
        try {
            final UserRsDTO updatedUser = this.usersService.updateUser(USER_ID, userNewData);
            objectsAreEqual(userNewData, updatedUser);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserNotExistsWhenUpdateThenNotFound() {
        when(this.usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        try {
            this.usersService.updateUser(USER_ID, createUserRequest("1112233"));
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.VALUE_NOT_FOUND, ApiConstants.USER_NOT_FOUND_MSG, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserDataIsNullWhenUpdateThenBadRequest() {
        try {
            this.usersService.updateUser(USER_ID, null);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_DATA_NOT_NULL, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenEmptyFirstNameWhenUpdateThenBadRequest() {
        final UserRqDTO userNewData = createUserRequest();
        userNewData.setFirstName("");
        try {
            this.usersService.updateUser(USER_ID, userNewData);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.FIRST_NAME_NOT_BLANK, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void whenDeleteThenOk() {
        when(this.usersRepository.findById(USER_ID)).thenReturn(Optional.of(createPostSaveUserEntity()));
        try {
            this.usersService.deleteUser(USER_ID);
            verify(this.usersRepository, atLeastOnce()).delete(any(User.class));
        } catch (ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserNotExistsWhenDeleteThenNotFound() {
        when(this.usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        try {
            this.usersService.deleteUser(USER_ID);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.VALUE_NOT_FOUND, ApiConstants.USER_NOT_FOUND_MSG, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

    @Test
    void givenUserIdIsNullWhenDeleteThenBadRequest() {
        try {
            this.usersService.deleteUser(null);
            fail("Should never continue execution");
        } catch (final BadRequestException e) {
            objectsAreEqual(ErrorEnum.INVALID_FIELD_VALUE, ApiConstants.USER_ID_NOT_NULL, e);
        } catch (final ApiException e) {
            fail("Should never throw this exception", e);
        }
    }

}