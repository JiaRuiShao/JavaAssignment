CREATE
DATABASE student_library;
USE
student_library;

SET
FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS borrow_history;
SET
FOREIGN_KEY_CHECKS = 1;

CREATE TABLE student (
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    name                 varchar(255) NOT NULL,
    borrowed_books_count INT DEFAULT 0
);

CREATE TABLE book (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    title    varchar(255) NOT NULL,
    borrowed BOOLEAN DEFAULT 0,
    isbn     varchar(255) NOT NULL UNIQUE,
    genre    varchar(255)
);
-- other possible fields: publisher, publicationDate, language, numberOfPages, List<Author> authors, Rack placedAt

CREATE TABLE borrow_history (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    student_id  INT,
    book_id     INT,
    fine        INT       DEFAULT 0,
    borrow_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_time    TIMESTAMP,
    return_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO student (name)
VALUES ('Larrie Shao');
INSERT INTO student (name)
VALUES ('Jason Matt');

SELECT *
FROM student;

INSERT INTO book (title, isbn, genre)
VALUES ('ABCDE My Book', 'asdfhjkebkfgj', 'action');
INSERT INTO book (title, isbn, genre)
VALUES ('Accounting', 'asfdsgfgjtrt', 'academy');
INSERT INTO book (title, isbn, genre)
VALUES ('finance', 'NHSDBK1238', 'financial');
INSERT INTO book (title, isbn, genre)
VALUES ('history', 'HIS-3w45789345', 'history');
INSERT INTO book (title, isbn, genre)
VALUES ('Six Pennies', 'MJGSI', 'others');

SELECT *
FROM book;

-- check after student 1 borrows book 1

SELECT *
FROM borrow_history;

SELECT *
FROM book
WHERE id = 1;

SELECT *
FROM student
WHERE id = 1;