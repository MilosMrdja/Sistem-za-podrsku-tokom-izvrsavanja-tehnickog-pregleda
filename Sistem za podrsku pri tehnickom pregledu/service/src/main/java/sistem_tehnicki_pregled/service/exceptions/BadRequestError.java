package sistem_tehnicki_pregled.service.exceptions;

import java.util.HashMap;
import java.util.Map;

public class BadRequestError extends RuntimeException {
    private final Map<String, String> errors;

    public BadRequestError(final Map<String, String> errors) {
        this.errors = errors;
    }

    public BadRequestError(final String message) {
        super(message);
        errors = new HashMap<>();
    }

    public BadRequestError(final String message, final Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
