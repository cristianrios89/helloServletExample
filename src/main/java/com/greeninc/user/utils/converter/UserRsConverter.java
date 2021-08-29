package com.greeninc.user.utils.converter;

import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link User} entity into a {@link UserRsDTO}.
 */
@Component
public class UserRsConverter implements Converter<User, UserRsDTO> {

    @Override
    public UserRsDTO convert(final User user) {
        final UserRsDTO convertedUser = new UserRsDTO();
        convertedUser.setId(user.getId());
        convertedUser.setFirstName(user.getFirstName());
        convertedUser.setLastName(user.getLastName());
        convertedUser.setEmail(user.getEmail());
        convertedUser.setPhone(user.getPhone());
        return convertedUser;
    }

}
