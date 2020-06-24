package at.ac.tuwien.sepm.groupphase.backend.exception;

import at.ac.tuwien.sepm.groupphase.backend.dto.ErrorDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.StringJoiner;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        StringJoiner joiner = new StringJoiner("\n");
        ex.getBindingResult()
            .getFieldErrors()
            .forEach(err -> joiner.add(err.getField() + " " + err.getDefaultMessage()));
        String errorMessage = joiner.toString();

        LOGGER.info("Handle method argument not valid with errors: {}", errorMessage);
        return getErrorResponse(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
        HttpStatus status, WebRequest request) {
        LOGGER.info("Handle exception {} with status {}", ex.getMessage(), status);
        return getErrorResponse(ex.getMessage(), status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedHandler(AccessDeniedException e) {
        LOGGER.info("Handle access denied exception with message {}", e.getMessage());
        return getErrorResponse("Nicht eingeloggt oder keine Rechte", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Object> duplicateEntityHandler(DuplicateEntityException e) {
        LOGGER.info("Handle duplicate entity exception with message {}", e.getMessage());
        return getErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<Object> validationErrorHandler(BusinessValidationException e) {
        StringJoiner joiner = new StringJoiner("\n");

        e.getValidationMessages().forEach(joiner::add);
        String errorMessage = joiner.toString();

        LOGGER.info("Handle business validation exception with errors: {}", errorMessage);
        return getErrorResponse(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundErrorHandler(NotFoundException e) {
        LOGGER.info("Handle not found exception with message {}", e.getMessage());
        return getErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaveFileException.class)
    public ResponseEntity<Object> saveFileErrorHandler(SaveFileException e) {
        LOGGER.info("Handle save file found exception with message {}", e.getMessage());
        return getErrorResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> dataIntegrityException(DataIntegrityViolationException e) {
        LOGGER.info("Handle data integrity found exception with message {}", e.getMessage());
        return getErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> genericExceptionHandler(Exception e) {
        LOGGER.error(
            "Uncaught Exception: " + e.getMessage() + " with stacktrace " + Arrays.toString(e.getStackTrace()));
        return getFatalErrorResponse("Internal Server Error: " + e.getMessage());
    }

    private ResponseEntity<Object> getErrorResponse(String message, HttpStatus returnStatus) {
        return new ResponseEntity<>(new ErrorDTO().message(message).type(ErrorType.WARN), returnStatus);
    }

    private ResponseEntity<Object> getFatalErrorResponse(String message) {
        return new ResponseEntity<>(new ErrorDTO().message(message).type(ErrorType.FATAL),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}