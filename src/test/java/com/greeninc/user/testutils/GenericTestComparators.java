package com.greeninc.user.testutils;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.models.dto.rq.UserRqDTO;
import com.greeninc.user.models.dto.rs.UserRsDTO;
import com.greeninc.user.models.entity.User;
import com.greeninc.user.models.enums.ErrorEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GenericTestComparators {

    private GenericTestComparators() {

    }

    public static void objectsAreEqual(final UserRqDTO rqDto, final UserRsDTO rsDto) {
        assertEquals(rqDto.getFirstName(), rsDto.getFirstName());
        assertEquals(rqDto.getLastName(), rsDto.getLastName());
        assertEquals(rqDto.getEmail(), rsDto.getEmail());
        assertEquals(rqDto.getPhone(), rsDto.getPhone());
        assertNotNull(rsDto.getId());
    }

    public static void objectsAreEqual(final User entity, final UserRsDTO rsDto) {
        assertEquals(entity.getFirstName(), rsDto.getFirstName());
        assertEquals(entity.getLastName(), rsDto.getLastName());
        assertEquals(entity.getEmail(), rsDto.getEmail());
        assertEquals(entity.getPhone(), rsDto.getPhone());
        assertEquals(entity.getId(), rsDto.getId());
    }

    public static void objectsAreEqual(final UserRqDTO rqDto, final User entity) {
        assertEquals(rqDto.getFirstName(), entity.getFirstName());
        assertEquals(rqDto.getLastName(), entity.getLastName());
        assertEquals(rqDto.getEmail(), entity.getEmail());
        assertEquals(rqDto.getPhone(), entity.getPhone());
        assertNull(entity.getId());
    }

    public static void objectsAreEqual(final ErrorEnum errorEnum, final String errorMsg, final ApiException exception) {
        assertEquals(errorEnum.getCode(), exception.getCode());
        assertEquals(errorEnum.getType(), exception.getType());
        assertEquals(errorMsg, exception.getMessage());
    }

}
