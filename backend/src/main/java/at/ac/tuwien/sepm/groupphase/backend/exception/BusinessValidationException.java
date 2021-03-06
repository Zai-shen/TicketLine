package at.ac.tuwien.sepm.groupphase.backend.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessValidationException extends RuntimeException {
    private List<String> validationMessages;

    public BusinessValidationException() {
        this(new ArrayList<>());
    }

    public BusinessValidationException(String message) {
        super(message);
        validationMessages = Collections.singletonList(message);
    }

    public BusinessValidationException(List<String> messages) {
        super("Validation failed" + (messages != null ? " with " + messages.size() + " errors" : ""));
        validationMessages = messages;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }
}
