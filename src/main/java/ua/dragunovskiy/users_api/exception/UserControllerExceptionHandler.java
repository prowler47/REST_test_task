package ua.dragunovskiy.users_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler(MyUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(MyUserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MyInvalidUserDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserDataException(MyInvalidUserDataException ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_USER_DATA", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}