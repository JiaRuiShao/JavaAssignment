package rest.demo.studentslibrary.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "isbn", nullable = false, unique = true)
    private String ISBN;

    @Column(nullable = false)
    private String title;

    private String genre;

    @Column(columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean borrowed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id == book.getId() && ISBN.equals(book.getISBN()) &&
                title.equals(book.getTitle()) && genre.equals(book.getGenre());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "ISBN = " + ISBN + ", " +
                "title = " + title + ", " +
                "genre = " + genre + ", " +
                "borrowed = " + borrowed + ")";
    }
}
