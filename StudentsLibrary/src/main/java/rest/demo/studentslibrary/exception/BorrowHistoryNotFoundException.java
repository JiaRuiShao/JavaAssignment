package rest.demo.studentslibrary.exception;

public class BorrowHistoryNotFoundException extends RuntimeException {
    public BorrowHistoryNotFoundException() {
    }

    public BorrowHistoryNotFoundException(String message) {
        super(message);
    }

    public BorrowHistoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BorrowHistoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public BorrowHistoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
