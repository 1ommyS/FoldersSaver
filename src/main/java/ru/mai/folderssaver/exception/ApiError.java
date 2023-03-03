package ru.mai.folderssaver.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author 1ommy
 * @version 28.02.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus status;
    private String message;
    private ApiErrorType type;
}