package at.ac.tuwien.sepm.groupphase.backend.exception;

public class SaveFileException extends RuntimeException {

    public SaveFileException() {
    }

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveFileException(Exception e) {
        super(e);
    }
}
