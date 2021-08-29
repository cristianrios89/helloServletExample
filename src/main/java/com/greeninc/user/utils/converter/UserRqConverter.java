package com.greeninc.user.utils.converter;

import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link UserRqDTO} into a {@link User} entity.
 */
@Component
public class UserRqConverter implements Converter<UserRqDTO, User> {

    @Override
    public User convert(final UserRqDTO userRqDTO) {
        final User convertedUser = new User();
        convertedUser.setFirstName(userRqDTO.getFirstName());
        convertedUser.setLastName(userRqDTO.getLastName());
        convertedUser.setEmail(userRqDTO.getEmail());
        convertedUser.setPhone(userRqDTO.getPhone());
        return convertedUser;
    }

}
