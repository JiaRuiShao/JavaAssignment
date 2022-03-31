package rest.demo.studentslibrary.exception;

public class StudentBorrowLimitException extends RuntimeException {
    public StudentBorrowLimitException() {
    }

    public StudentBorrowLimitException(String message) {
        super(message);
    }

    public StudentBorrowLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentBorrowLimitException(Throwable cause) {
        super(cause);
    }

    public StudentBorrowLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
