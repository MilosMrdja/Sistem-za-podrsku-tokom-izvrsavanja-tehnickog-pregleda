package sistem_tehnicki_pregled.service.exceptions;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ServerError extends RuntimeException{
    private Integer code;

    public ServerError(String message, @Nullable @Min(500) @Max(599) Integer code) {
        super(message);
        this.code = code;
    }
}
