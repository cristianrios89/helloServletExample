package com.greeninc.user.models.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO meant to be used when returning an error to the consumer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRsDTO {

    private int code;
    private String type;
    private String message;

}
