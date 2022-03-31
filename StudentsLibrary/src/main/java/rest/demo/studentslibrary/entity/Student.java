package rest.demo.studentslibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Student {
    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "borrowed_books_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int borrowedBooksCount;

    // fetch = FetchType.EAGER -- slow performance
    // cascade = CascadeType.PERSIST -- used when we want to keep the authorizes when the user got deleted
    // mappedBy = foreignKey
    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "student")
    // private List<Book> borrowedBooks;
}
