package coderuz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
//@AllArgsConstructor
public class PageResponse<T> {
    List<T> content;
    private long totalCount;
}
