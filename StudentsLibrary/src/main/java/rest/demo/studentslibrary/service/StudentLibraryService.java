package rest.demo.studentslibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.demo.studentslibrary.dao.BookRepo;
import rest.demo.studentslibrary.dao.BorrowHistoryRepo;
import rest.demo.studentslibrary.dao.StudentRepo;
import rest.demo.studentslibrary.dto.BorrowHistoryResponse;
import rest.demo.studentslibrary.dto.PagedResponse;
import rest.demo.studentslibrary.entity.Book;
import rest.demo.studentslibrary.entity.BorrowHistory;
import rest.demo.studentslibrary.entity.Student;
import rest.demo.studentslibrary.exception.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentLibraryService {

    private static final int STUDENT_BORROW_LIMIT = 5;
    private BookRepo bookRepo;
    private StudentRepo studentRepo;
    private BorrowHistoryRepo borrowHistoryRepo;

    @Autowired
    public StudentLibraryService(BookRepo bookRepo, StudentRepo studentRepo, BorrowHistoryRepo borrowHistoryRepo) {
        this.bookRepo = bookRepo;
        this.studentRepo = studentRepo;
        this.borrowHistoryRepo = borrowHistoryRepo;
    }

    private Pageable validateAndGetPageable(int page, int size, String direction, String orderBy) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }

        Sort sort = Sort.by(Sort.Direction.ASC, orderBy);
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, orderBy);
        } else if (!direction.equalsIgnoreCase("asc")) {
            throw new BadRequestException("Sort Direction has to be either ASC or DESC");
        }
        return PageRequest.of(page, size, sort);
    }

    private PagedResponse getPagedResponse(Page<Book> books) {
        List<Book> content = books.getNumberOfElements() == 0 ? Collections.emptyList() : books.getContent();
        return PagedResponse.builder().content(content.stream().map(
                e -> Book.builder().id(e.getId()).borrowed(e.isBorrowed()).ISBN(e.getISBN()).title(e.getTitle()).build())
                .collect(Collectors.toList())).page(books.getNumber()).size(books.getSize()).totalElements(books.getTotalElements())
                .totalPages(books.getTotalPages()).last(books.getNumber() == books.getTotalPages()).build();
    }

    public PagedResponse findAllBooks(int page, int size, String direction, String orderBy) {
        Pageable pageableRequest = validateAndGetPageable(page, size, direction, orderBy);
        Page<Book> books = bookRepo.findAll(pageableRequest);
        return getPagedResponse(books);
    }

    public PagedResponse findByBookTitle(String title, int page, int size, String direction, String orderBy) {
        Pageable pageableRequest = validateAndGetPageable(page, size, direction, orderBy);
        Page<Book> books = bookRepo.findByTitle(title, pageableRequest);
        return getPagedResponse(books);
    }

    public PagedResponse findByBookTitleLike(String title, int page, int size, String direction, String orderBy) {
        Pageable pageableRequest = validateAndGetPageable(page, size, direction, orderBy);
        Page<Book> books = bookRepo.findByTitleLike("%" + title + "%", pageableRequest);
        return getPagedResponse(books);
    }

    public PagedResponse findByBookGenre(String genre, int page, int size, String direction, String orderBy) {
        Pageable pageableRequest = validateAndGetPageable(page, size, direction, orderBy);
        Page<Book> books = bookRepo.findByGenre(genre, pageableRequest);
        return getPagedResponse(books);
    }

    public Book findByBookId(long id) throws BookNotFoundException {
        Optional<Book> book = bookRepo.findById(id);
        if (!book.isPresent()) throw new BookNotFoundException("BOOK_NOT_FOUND");
        return book.get();
    }

    /*=================================== WITHOUT PagedResponse ===========================================*/

    public List<Book> findByBookTitle(String title) {
        return bookRepo.findByTitleLike("%" + title + "%");
    }

    public List<Book> findByBookGenre(String genre) {
        return bookRepo.findByGenre(genre);
    }

    /*=================================== Librarian Methods ===========================================*/
    @Transactional
    public BorrowHistoryResponse borrowBook(long studentId, long bookId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFoundException("STUDENT_NOT_FOUND"));
        if (student.getBorrowedBooksCount() >= STUDENT_BORROW_LIMIT) throw new StudentBorrowLimitException("STUDENT_BORROW_LIMIT_REACHED");
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException("BOOK_NOT_FOUND"));
        if (book.isBorrowed()) throw new BookIsNotAvailableException("BOOK_NOT_AVAILABLE");
        book.setBorrowed(true);
        // the List<Book> books in Student is not updated
        // student.setBorrowedBooks(new ArrayList<Book>(student.getBorrowedBooks().add(book)));
        student.setBorrowedBooksCount(student.getBorrowedBooksCount() + 1);
        BorrowHistory borrowHistory = borrowHistoryRepo.save(BorrowHistory.builder().student(student).book(book).build());
        return BorrowHistoryResponse.builder().status(HttpStatus.OK.toString()).borrowHistoryId(borrowHistory.getId())
                .student(borrowHistory.getStudent()).book(borrowHistory.getBook()).build();
    }

    @Transactional
    public BorrowHistoryResponse returnBook(long studentId, long bookId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFoundException("STUDENT_NOT_FOUND"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException("BOOK_NOT_FOUND"));
        BorrowHistory borrowHistory = borrowHistoryRepo.findById(student.getId(), book.getId()).orElseThrow(() -> new BorrowHistoryNotFoundException("BOOK_NOT_FOUND"));
        book.setBorrowed(false);
        student.setBorrowedBooksCount(student.getBorrowedBooksCount() - 1);
        borrowHistoryRepo.delete(borrowHistory);
        return BorrowHistoryResponse.builder().status(HttpStatus.OK.toString()).borrowHistoryId(borrowHistory.getId())
                .student(borrowHistory.getStudent()).book(borrowHistory.getBook()).build();
    }
}
