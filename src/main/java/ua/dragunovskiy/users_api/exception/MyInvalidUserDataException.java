package ua.dragunovskiy.users_api.exception;

public class MyInvalidUserDataException extends RuntimeException {
    public MyInvalidUserDataException(String message) {
        super(message);
    }
}
