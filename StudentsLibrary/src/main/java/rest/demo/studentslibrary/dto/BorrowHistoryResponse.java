package rest.demo.studentslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rest.demo.studentslibrary.entity.Book;
import rest.demo.studentslibrary.entity.Student;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowHistoryResponse {
    private String status;
    private long borrowHistoryId;
    private Student student;
    private Book book;
}
