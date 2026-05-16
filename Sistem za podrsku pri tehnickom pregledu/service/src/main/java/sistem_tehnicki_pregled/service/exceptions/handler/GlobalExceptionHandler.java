package sistem_tehnicki_pregled.service.exceptions.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import sistem_tehnicki_pregled.service.exceptions.BadRequestError;
import sistem_tehnicki_pregled.service.exceptions.NotFoundError;
import sistem_tehnicki_pregled.service.exceptions.ServerError;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, Object> buildBody(HttpStatus status, String error, String message, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return body;
    }

    @ExceptionHandler(BadRequestError.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestError ex, WebRequest request) {
        return new ResponseEntity<>(
                buildBody(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request),
                HttpStatus.BAD_REQUEST
        );
    }

//    @ExceptionHandler(UnauthorizedError.class)
//    public ResponseEntity<Object> handleUnauthorized(UnauthorizedError ex, WebRequest request) {
//        return new ResponseEntity<>(
//                buildBody(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), request),
//                HttpStatus.FORBIDDEN
//        );
//    }

    @ExceptionHandler(NotFoundError.class)
    public ResponseEntity<Object> handleNotFound(NotFoundError ex, WebRequest request) {
        return new ResponseEntity<>(
                buildBody(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage(), request),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ServerError.class)
    public ResponseEntity<Object> handleServerError(ServerError ex, WebRequest request) {
        return new ResponseEntity<>(
                buildBody(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), request),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Validation failed");

        return new ResponseEntity<>(
                buildBody(HttpStatus.BAD_REQUEST, "Validation Failed", errors, request),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                buildBody(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage(), request),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
