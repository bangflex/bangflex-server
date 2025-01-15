package springbootmonolithic.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageResponse<T> {

    private T data;             // 현재 페이지의 데이터
    private int pageNumber;     // 현재 페이지 번호
    private int pageSize;       // 페이지 크기
    private int totalCount;     // 총 데이터 개수
    private int totalPages;     // 총 페이지 수

    public PageResponse(T data, int pageNumber, int pageSize, int totalCount) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }
}
