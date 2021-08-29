package com.greeninc.user.models.dto.rs;

import com.greeninc.user.models.dto.common.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO meant to be returned when creating or updating a user entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRsDTO extends UserDto {

    private Long id;

}
