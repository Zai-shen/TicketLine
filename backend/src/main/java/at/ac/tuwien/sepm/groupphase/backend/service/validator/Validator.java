package at.ac.tuwien.sepm.groupphase.backend.service.validator;

import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic validator for business constraint validation on the service level example usage: new
 * EmailValidator().build(email).and(new PasswordValidator().build(password)).validate()
 *
 * @param <T> type of the object to validate
 */
public abstract class Validator<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    private List<String> errorMessages = new ArrayList<>();
    private boolean hasBuiltMessages = false;

    /**
     * Build the constraints and error messages
     *
     * @param object to build messages and validate against
     */
    public Validator<T> build(T object) {
        if (!hasBuiltMessages) {
            doValidation(object);
            hasBuiltMessages = true;
        }
        return this;
    }

    /**
     * Extend the validator with the constraints of another validator
     *
     * @param validator to extend this validator with
     * @param <S>       type of the object the new validator validates
     * @throws RuntimeException if the error messages haven't been built yet for any of this or the other validator
     */
    public <S> Validator<S> and(Validator<S> validator) {
        if (!hasBuiltMessages || !validator.hasBuiltMessages) {
            throw new RuntimeException(
                "Error in validator call: You need to build(x) the constraints for all validators first before extending with and(y)");
        }
        errorMessages.addAll(validator.errorMessages);
        return validator;
    }

    /**
     * Validate against all constraints of all validators
     *
     * @throws RuntimeException            if the error messages haven't been built yet for this validator
     * @throws BusinessValidationException with all fired constraint error messages
     */
    public void validate() {
        if (!hasBuiltMessages) {
            throw new RuntimeException(
                "Error in validator call: You need to build(x) the constraints first before validating");
        }
        if (!errorMessages.isEmpty()) {
            LOGGER.info("Validation failed with {} errors", errorMessages.size());
            throw new BusinessValidationException(errorMessages);
        }
    }

    /**
     * Implements the business validation constraints
     *
     * @param object to validate against
     */
    protected abstract void doValidation(T object);

    protected void validOrMessage(boolean condition, String message) {
        if (!condition) {
            LOGGER.debug("Validation error with message '" + message + "'");
            errorMessages.add(message);
        }
    }

    protected void notNullOrName(Object object, String name) {
        validOrMessage(object != null, name + " ist nicht gesetzt");
    }

    protected void notEmptyOrName(String string, String name) {
        validOrMessage(string != null && !string.trim().isEmpty(), name + " ist leer");
    }

    protected <S> void callValidatorOnChild(Validator<S> validator, S child) {
        if (child != null) {
            validator.build(child);
            errorMessages.addAll(validator.errorMessages);
        }
    }
}
