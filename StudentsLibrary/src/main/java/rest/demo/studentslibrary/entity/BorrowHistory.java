package rest.demo.studentslibrary.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrow_history")
public class BorrowHistory {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int fine;

    // @Column(name = "borrow_time", updatable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    // @CreatedDate
    // private Date borrowDate; // LocalDateTime
    @Column(name = "borrow_time")
    @CreationTimestamp
    private java.sql.Timestamp borrowDate;

    @Column(name = "due_time")
    // private Date dueDate; // LocalDateTime
    private java.sql.Timestamp dueDate;

    // @Column(name = "return_time")
    // @Temporal(TemporalType.TIMESTAMP)
    // @LastModifiedDate
    // private Date returnDate; // LocalDateTime
    @Column(name = "return_time")
    @UpdateTimestamp
    private java.sql.Timestamp returnDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BorrowHistory that = (BorrowHistory) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
