package springbootmonolithic.security.exception;


import org.springframework.security.core.AuthenticationException;

public class NotAuthenticatedException extends AuthenticationException {
    public NotAuthenticatedException(String msg) {
        super(msg);
    }
}
