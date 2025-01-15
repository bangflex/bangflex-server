package springbootmonolithic.common.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class SuccessResponse<T> {
    private final String message;
    private final T result;
    private LocalDateTime timestamp;
}
