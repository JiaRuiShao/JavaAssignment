## Domain/Entity
- Student
  - id
  - name
  - borrowedBooksCount
  - List<Book> books
- Book
  - id
  - title
  - isbn
  - genre
  - borrowed
  - student
  - borrowHistory
  - others
    - publisher
    - publicationDate
    - language
    - numberOfPages
    - List<Author> authors
    - Rack placedAt
- BorrowHistory
  - id
  - student
  - book
  - borrowData
  - returnDate
  - dueDate
  - fine