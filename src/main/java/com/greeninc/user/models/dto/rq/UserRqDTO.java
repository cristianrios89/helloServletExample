package com.greeninc.user.models.dto.rq;

import com.greeninc.user.models.dto.common.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO meant to be consumed when creating or updating a user entity.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRqDTO extends UserDto {

}
