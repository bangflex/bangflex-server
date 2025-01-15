package springbootmonolithic.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springbootmonolithic.common.response.ErrorResponse;
import springbootmonolithic.exception.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // 400: 잘못된 요청 예외 처리
    @ExceptionHandler({
            BadRequestException.class,  // base BadRequestException error message
            ConstraintViolationException.class,
            InvalidDataException.class,
            EmailDuplicatedException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        logger.error(e.getMessage(), e);
        String errorMessage = "잘못된 요청입니다.";

        if (isKnownBadRequestException(e)) {
            if (e instanceof ConstraintViolationException cve) {
                errorMessage = cve.getConstraintViolations()
                        .stream()
                        .findFirst()
                        .map(ConstraintViolation::getMessage)
                        .orElse(errorMessage);
            } else {
                errorMessage = e.getMessage();
            }
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    // 401: 권한 없는 사용자
    @ExceptionHandler({
            AuthenticationException.class,  // base Authentication error message
            InvalidMemberException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidUserException(Exception e) {
        logger.error(e.getMessage(), e);
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
    }

    // 403: FORBIDDEN
    @ExceptionHandler({
            AccessDeniedException.class,    // base Authorization error message
    })
    public ResponseEntity<ErrorResponse> handleForbiddenException(Exception e) {
        logger.error(e.getMessage(), e);
        return buildErrorResponse(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }

    // 404: Not Found
    @ExceptionHandler({
            NotFoundException.class,        // base NotFound error message
            MemberNotFoundException.class,
            BoardNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        String errorMessage = "정보를 찾을 수 없습니다.";
        if (isKnownNotFoundRequestException(e)) {
            errorMessage = e.getMessage();
        }

        return buildErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
    }

    // 500: Internal Server Error (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        logger.error("Unexpected error occurred", e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 에러입니다. 관리자에게 문의하세요.");
    }


    private boolean isKnownBadRequestException(Exception e) {
        return e instanceof InvalidDataException ||
                e instanceof ConstraintViolationException ||
                e instanceof EmailDuplicatedException;
    }

    private boolean isKnownNotFoundRequestException(Exception e) {
        return e instanceof MemberNotFoundException ||
                e instanceof BoardNotFoundException;
    }
}
