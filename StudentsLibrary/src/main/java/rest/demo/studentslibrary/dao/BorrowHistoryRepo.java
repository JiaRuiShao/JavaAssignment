package rest.demo.studentslibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.demo.studentslibrary.entity.BorrowHistory;

import java.util.Optional;

@Repository
public interface BorrowHistoryRepo extends JpaRepository<BorrowHistory, Long> {
    @Query("SELECT bs FROM BorrowHistory bs WHERE bs.student.id = :studentId AND bs.book.id = :bookId")
    Optional<BorrowHistory> findById(long studentId, long bookId);
}
