package com.greeninc.user.utils.converter;

import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.entity.User;
import com.greeninc.user.testutils.GenericTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.greeninc.user.testutils.GenericTestComparators.objectsAreEqual;

@ExtendWith(MockitoExtension.class)
class UserRqConverterTest {

    @Spy
    private UserRqConverter userRqConverter;

    @Test
    void whenConvertThenOk() {
        final UserRqDTO userToConvert = GenericTestData.createUserRequest();
        final User convertedUser = this.userRqConverter.convert(userToConvert);
        objectsAreEqual(userToConvert, convertedUser);
    }

}