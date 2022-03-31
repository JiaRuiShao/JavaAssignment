package rest.demo.studentslibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.demo.studentslibrary.dto.BorrowHistoryResponse;
import rest.demo.studentslibrary.dto.PagedResponse;
import rest.demo.studentslibrary.entity.Book;
import rest.demo.studentslibrary.exception.*;
import rest.demo.studentslibrary.service.StudentLibraryService;

@RestController
@RequestMapping("/api")
public class StudentLibraryController {
    StudentLibraryService libraryService;

    @Autowired
    public StudentLibraryController(StudentLibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/book/{id}")
    public Book findBook(@PathVariable long id) throws BadRequestException, BookNotFoundException {
        return libraryService.findByBookId(id);
    }

    @GetMapping("/book/title/{title}")
    public PagedResponse findByBookTitle(@PathVariable String title,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                         @RequestParam(required = false, defaultValue = "title") String orderBy,
                                         @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction)
            throws BadRequestException {
        return libraryService.findByBookTitleLike(title, page, size, direction, orderBy);
    }

    @GetMapping("/book/genre/{genre}")
    public PagedResponse findByBookGenre(@PathVariable String genre,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                         @RequestParam(required = false, defaultValue = "title") String orderBy,
                                         @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction)
            throws BadRequestException {
        return libraryService.findByBookGenre(genre, page, size, direction, orderBy);
    }

    @GetMapping("/book")
    public PagedResponse findAllBooks(@RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam(required = false, defaultValue = "20") int size,
                                      @RequestParam(required = false, defaultValue = "title") String orderBy,
                                      @RequestParam(required = false, defaultValue = "ASC") String direction)
            throws BadRequestException {
        return libraryService.findAllBooks(page, size, direction, orderBy);
    }

    @PostMapping("/student/{sid}/book/{bookid}")
    public BorrowHistoryResponse borrowBook(@PathVariable long sid, @PathVariable long bookid)
            throws BadRequestException, StudentNotFoundException, StudentBorrowLimitException, BookNotFoundException, BookIsNotAvailableException {
        return libraryService.borrowBook(sid, bookid);
    }

    @DeleteMapping("/student/{sid}/book/{bookid}")
    public BorrowHistoryResponse returnBook(@PathVariable long sid, @PathVariable long bookid)
            throws BadRequestException, StudentNotFoundException, BookNotFoundException, BorrowHistoryNotFoundException {
        return libraryService.returnBook(sid, bookid);
    }
}
