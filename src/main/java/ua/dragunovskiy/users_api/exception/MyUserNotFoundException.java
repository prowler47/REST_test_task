package ua.dragunovskiy.users_api.exception;

public class MyUserNotFoundException extends RuntimeException{
    public MyUserNotFoundException(String message) {
        super(message);
    }
}
