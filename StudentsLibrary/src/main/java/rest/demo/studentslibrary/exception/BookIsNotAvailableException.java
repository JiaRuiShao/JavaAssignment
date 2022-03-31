package rest.demo.studentslibrary.exception;

public class BookIsNotAvailableException extends RuntimeException {
    public BookIsNotAvailableException() {
    }

    public BookIsNotAvailableException(String message) {
        super(message);
    }

    public BookIsNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookIsNotAvailableException(Throwable cause) {
        super(cause);
    }

    public BookIsNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
