package com.ffysVideo.exceptions;

/**
 * 用户登录异常
 */
public class UserLoginException extends RuntimeException{
    public UserLoginException() {
        super();
    }
    public UserLoginException(String message) {
        super(message);
    }
}
