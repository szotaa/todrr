package pl.szotaa.todrr.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rest error response entity.
 *
 * @author szotaa
 */

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String message;
    private final String info;
}
