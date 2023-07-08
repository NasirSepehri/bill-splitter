package com.example.billsplitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AppException extends RuntimeException {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFound extends RuntimeException {
        public NotFound(String msg, Throwable cause) {
            super(msg, cause);
        }

        public NotFound(String msg) {
            super(msg);
        }

        public NotFound(Throwable cause) {
            super(cause);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class ValidationFailure extends RuntimeException {
        public ValidationFailure(String msg, Throwable cause) {
            super(msg, cause);
        }

        public ValidationFailure(String msg) {
            super(msg);
        }

        public ValidationFailure(Throwable cause) {
            super(cause);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequest extends RuntimeException {
        public BadRequest(String msg, Throwable cause) {
            super(msg, cause);
        }

        public BadRequest(String msg) {
            super(msg);
        }

        public BadRequest(Throwable cause) {
            super(cause);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class AuthorizationFailed extends RuntimeException {
        public AuthorizationFailed(String msg, Throwable cause) {
            super(msg, cause);
        }

        public AuthorizationFailed(String msg) {
            super(msg);
        }

        public AuthorizationFailed(Throwable cause) {
            super(cause);
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class Forbidden extends RuntimeException {
        public Forbidden(String msg, Throwable cause) {
            super(msg, cause);
        }

        public Forbidden(String msg) {
            super(msg);
        }

        public Forbidden(Throwable cause) {
            super(cause);
        }
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class ServerError extends RuntimeException {
        public ServerError(String msg, Throwable cause) {
            super(msg, cause);
        }

        public ServerError(String msg) {
            super(msg);
        }

        public ServerError(Throwable cause) {
            super(cause);
        }
    }
}
