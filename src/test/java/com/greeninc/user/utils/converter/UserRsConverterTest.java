package com.greeninc.user.utils.converter;

import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.entity.User;
import com.greeninc.user.testutils.GenericTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.greeninc.user.testutils.GenericTestComparators.objectsAreEqual;

@ExtendWith(MockitoExtension.class)
class UserRsConverterTest {

    @Spy
    private UserRsConverter userRsConverter;

    @Test
    void whenConvertThenOk() {
        final User userToConvert = GenericTestData.createPostSaveUserEntity();
        final UserRsDTO convertedUser = this.userRsConverter.convert(userToConvert);
        objectsAreEqual(userToConvert, convertedUser);
    }

}