package sistem_tehnicki_pregled.service.exceptions;

public class NotFoundError extends RuntimeException{
    public NotFoundError(String message) {
        super(message);
    }

    public NotFoundError() {
        super();
    }
}
