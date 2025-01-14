package springbootmonolithic.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springbootmonolithic.exception.BoardNotFoundException;
import springbootmonolithic.exception.InvalidDataException;
import springbootmonolithic.exception.InvalidMemberException;
import springbootmonolithic.exception.MemberNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400: 잘못된 요청 예외 처리
    @ExceptionHandler({
            InvalidDataException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage<>(400, e.getMessage(), null));
    }

    // 401: 권한 없는 사용자
    @ExceptionHandler({
            InvalidMemberException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleInvalidUserException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseMessage<>(401, e.getMessage(), null));
    }

    // 403: FORBIDDEN


    // 404: Not Found
    @ExceptionHandler({
            MemberNotFoundException.class,
            BoardNotFoundException.class
    })
    public ResponseEntity<ResponseMessage<Object>> handleNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage<>(404, e.getMessage(), null));
    }
}
