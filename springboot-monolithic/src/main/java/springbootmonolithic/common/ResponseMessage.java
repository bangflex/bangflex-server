package springbootmonolithic.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage<T> {

    private int status;
    private String msg;
    private T result;
}
