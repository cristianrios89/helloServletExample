package com.greeninc.user.controller;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages all the operations associated to the users of the app.
 */
@RestController
@RequestMapping(path = "/users")
public class UsersController {

    private final UsersService usersService;

    /**
     * Constructs an instance of {@link UsersController} class.
     *
     * @param usersService to be injected.
     */
    @Autowired
    public UsersController(final UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Creates a new user.
     *
     * @param user to be created.
     * @return a {@link ResponseEntity} of {@link UserRsDTO} with the user just created.
     * @throws BadRequestException when the request is invalid.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRsDTO> createUser(@RequestBody(required = false) final UserRqDTO user) throws BadRequestException {
        return new ResponseEntity<>(this.usersService.createUser(user), HttpStatus.OK);
    }

    /**
     * Retrieves an existing user by id.
     *
     * @param userId to be retrieved.
     * @return a {@link ResponseEntity} of {@link UserRsDTO} with the searched user.
     * @throws ApiException when an error occurs.
     */
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRsDTO> retrieveUser(@PathVariable final Long userId) throws ApiException {
        return new ResponseEntity<>(this.usersService.retrieveUser(userId), HttpStatus.OK);
    }
	
	@GetMapping(path = "/test")
    public ResponseEntity<String> retrieveTestText() {
        return ResponseEntity.ok("Hello World from Tomcat!");
    }
	
    /**
     * Updates an existing user searched by id.
     *
     * @param userId to be updated.
     * @param user   with new data.
     * @return a {@link ResponseEntity} of {@link UserRsDTO} with the updated user.
     * @throws ApiException when an error occurs.
     */
    @PutMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRsDTO> updateUser(@PathVariable final Long userId, @RequestBody(required = false) final UserRqDTO user) throws ApiException {
        return new ResponseEntity<>(this.usersService.updateUser(userId, user), HttpStatus.OK);
    }

    /**
     * Deletes an existing user searched by id.
     *
     * @param userId of the user to be deleted.
     * @return a {@link ResponseEntity} of {@link HttpStatus}.
     * @throws ApiException when an error occurs.
     */
    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable final Long userId) throws ApiException {
        this.usersService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK, HttpStatus.OK);
    }


}
