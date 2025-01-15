package springbootmonolithic.common.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
