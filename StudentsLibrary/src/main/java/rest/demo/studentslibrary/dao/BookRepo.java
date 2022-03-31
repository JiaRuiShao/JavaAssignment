package rest.demo.studentslibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.demo.studentslibrary.entity.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    // Derived Query Method
    List<Book> findByTitleStartingWith(String title);
    List<Book> findByTitleLike(String s);
    List<Book> findByGenre(String genre);
    List<Book> findByTitle(String title);

    Page<Book> findByGenre(String genre, Pageable pageableRequest);
    Page<Book> findByTitle(String title, Pageable pageableRequest);
    Page<Book> findByTitleLike(String titlePattern, Pageable pageableRequest);
}
