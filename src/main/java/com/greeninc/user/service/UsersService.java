package com.greeninc.user.service;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;

/**
 * Manages the users inner business logic.
 */
public interface UsersService {

    /**
     * Creates a new user.
     *
     * @param user to create.
     * @return {@link UserRsDTO} The user just created.
     * @throws BadRequestException when the request is invalid.
     */
    public UserRsDTO createUser(final UserRqDTO user) throws BadRequestException;

    /**
     * Retrieves an existing user by id.
     *
     * @param userId id of the user to retrieve.
     * @return {@link UserRsDTO} The searched user.
     * @throws ApiException when an error occurs.
     */
    public UserRsDTO retrieveUser(final Long userId) throws ApiException;

    /**
     * Updates an existing user, searched by its id.
     *
     * @param userId    ID of the user to update.
     * @param userRqDTO New data to assign to the user
     * @return {@link UserRsDTO} The just updated user.
     * @throws ApiException when an error occurs.
     */
    public UserRsDTO updateUser(final Long userId, final UserRqDTO userRqDTO) throws ApiException;

    /**
     * Deletes an existing user by id.
     *
     * @param userId ID of the user to delete.
     * @throws ApiException when an error occurs.
     */
    public void deleteUser(final Long userId) throws ApiException;

}
