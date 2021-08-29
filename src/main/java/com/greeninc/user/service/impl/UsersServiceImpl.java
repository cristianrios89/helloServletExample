package com.greeninc.user.service.impl;

import com.greeninc.user.utils.converter.UserRqConverter;
import com.greeninc.user.utils.converter.UserRsConverter;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.entity.User;
import com.greeninc.user.models.enums.ErrorEnum;
import com.greeninc.user.exception.ApiException;
import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.repository.UsersRepository;
import com.greeninc.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.greeninc.user.models.constants.ApiConstants.USER_ALREADY_EXISTS;
import static com.greeninc.user.models.constants.ApiConstants.USER_NOT_FOUND_MSG;
import static com.greeninc.user.utils.validator.UserValidator.validateUserOnCreation;
import static com.greeninc.user.utils.validator.UserValidator.validateUserOnDelete;
import static com.greeninc.user.utils.validator.UserValidator.validateUserOnRetrieve;
import static com.greeninc.user.utils.validator.UserValidator.validateUserOnUpdate;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserRqConverter userRqConverter;
    private final UserRsConverter userRsConverter;

    @Autowired
    public UsersServiceImpl(final UsersRepository usersRepository, final UserRqConverter userRqConverter, final UserRsConverter userRsConverter) {
        this.usersRepository = usersRepository;
        this.userRqConverter = userRqConverter;
        this.userRsConverter = userRsConverter;
    }

    @Override
    public UserRsDTO createUser(final UserRqDTO user) throws BadRequestException {
        validateUserOnCreation(user);
        if (this.usersRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())) {
            throw new BadRequestException(ErrorEnum.ALREADY_EXISTS, USER_ALREADY_EXISTS);
        }
        final User convertedUser = this.userRqConverter.convert(user);
        final User savedUser = this.usersRepository.save(convertedUser);
        return this.userRsConverter.convert(savedUser);
    }

    @Override
    public UserRsDTO retrieveUser(final Long userId) throws ApiException {
        validateUserOnRetrieve(userId);
        return this.usersRepository
                .findById(userId).map(this.userRsConverter::convert)
                .orElseThrow(() -> new BadRequestException(ErrorEnum.VALUE_NOT_FOUND, USER_NOT_FOUND_MSG));
    }

    @Override
    public UserRsDTO updateUser(final Long userId, final UserRqDTO user) throws ApiException {
        validateUserOnUpdate(userId, user);
        final User userToUpdate = this.usersRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorEnum.VALUE_NOT_FOUND, USER_NOT_FOUND_MSG));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());
        final User updatedUser = this.usersRepository.save(userToUpdate);
        return this.userRsConverter.convert(updatedUser);
    }

    @Override
    public void deleteUser(final Long userId) throws ApiException {
        validateUserOnDelete(userId);
        final User userToDelete = this.usersRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorEnum.VALUE_NOT_FOUND, USER_NOT_FOUND_MSG));
        this.usersRepository.delete(userToDelete);
    }

}
