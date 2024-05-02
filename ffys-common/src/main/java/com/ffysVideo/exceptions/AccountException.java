package com.ffysVideo.exceptions;

/**
 * 用户登录异常
 */
public class AccountException extends RuntimeException{
    public AccountException() {
        super();
    }
    public AccountException(String message) {
        super(message);
    }
}
