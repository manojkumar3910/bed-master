package com.bedmaster.inventory.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Hidden  // ✅ IMPORTANT: Tells Swagger to ignore this class
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 404 – Resource Not Found (Facility / Unit / Room / Bed)
    @ExceptionHandler({
            FacilityNotFoundException.class,
            UnitNotFoundException.class,
            RoomNotFoundException.class,
            BedNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(RuntimeException ex) {
        return new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage()
        );
    }

    // ✅ 400 – Invalid Status / Invalid Transition
    @ExceptionHandler(InvalidStatusTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidStatus(InvalidStatusTransitionException ex) {
        return new ErrorResponse(
                "INVALID_STATUS",
                ex.getMessage()
        );
    }

    // ✅ 400 – Validation Errors (@Valid failures)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + " : " + error.getDefaultMessage()
                )
                .collect(Collectors.toList());

        return new ErrorResponse(
                "VALIDATION_FAILED",
                errors
        );
    }

    // ✅ 500 – Fallback (unexpected errors)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        return new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage()
        );
    }
}
