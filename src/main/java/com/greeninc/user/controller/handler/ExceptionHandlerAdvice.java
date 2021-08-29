package com.greeninc.user.controller.handler;

import com.greeninc.user.exception.ApiException;
import com.greeninc.user.exception.BadRequestException;
import com.greeninc.user.exception.NotFoundException;
import com.greeninc.user.models.dto.rs.ErrorRsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.greeninc.user.models.enums.ErrorEnum.INTERNAL_SERVER_ERROR;

/**
 * Handles all the exceptions thrown in the app.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * Handles the {@link BadRequestException} exceptions.
     *
     * @param badRequestException to be handled.
     * @return a {@link ResponseEntity} of {@link ErrorRsDTO} with the error details.
     */
    @ExceptionHandler(value = BadRequestException.class)
    public final ResponseEntity<ErrorRsDTO> handleApiException(final BadRequestException badRequestException) {
        final ErrorRsDTO errorRsDTO = new ErrorRsDTO();
        errorRsDTO.setCode(badRequestException.getCode());
        errorRsDTO.setType(badRequestException.getType());
        errorRsDTO.setMessage(badRequestException.getMessage());
        return new ResponseEntity<>(errorRsDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link NotFoundException} exceptions.
     *
     * @param notFoundException to be handled.
     * @return a {@link ResponseEntity} of {@link ErrorRsDTO} with the error details.
     */
    @ExceptionHandler(value = NotFoundException.class)
    public final ResponseEntity<ErrorRsDTO> handleApiException(final NotFoundException notFoundException) {
        final ErrorRsDTO errorRsDTO = new ErrorRsDTO();
        errorRsDTO.setCode(notFoundException.getCode());
        errorRsDTO.setType(notFoundException.getType());
        errorRsDTO.setMessage(notFoundException.getMessage());
        return new ResponseEntity<>(errorRsDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link ApiException} exceptions.
     *
     * @param apiException to be handled.
     * @return a {@link ResponseEntity} of {@link ErrorRsDTO} with the error details.
     */
    @ExceptionHandler(value = ApiException.class)
    public final ResponseEntity<ErrorRsDTO> handleApiException(final ApiException apiException) {
        final ErrorRsDTO errorRsDTO = new ErrorRsDTO();
        errorRsDTO.setCode(apiException.getCode());
        errorRsDTO.setType(apiException.getType());
        errorRsDTO.setMessage(apiException.getMessage());
        return new ResponseEntity<>(errorRsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles the generic unexpected exceptions.
     *
     * @param exception to be handled.
     * @return a {@link ResponseEntity} of {@link ErrorRsDTO} with the error details.
     */
    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<ErrorRsDTO> handleApiException(final Exception exception) {
        final ErrorRsDTO errorRsDTO = new ErrorRsDTO();
        errorRsDTO.setCode(INTERNAL_SERVER_ERROR.getCode());
        errorRsDTO.setType(INTERNAL_SERVER_ERROR.getType());
        errorRsDTO.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorRsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
