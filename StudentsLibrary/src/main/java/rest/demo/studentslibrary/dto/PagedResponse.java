package rest.demo.studentslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rest.demo.studentslibrary.entity.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse {
    private List<Book> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
