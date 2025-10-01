package coderuz.dto;

import coderuz.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private Integer id;
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 50)
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 5, max = 50)
    private String surname;

    @NotNull(message = "Level is required")
    @Min(value = 1, message = "Level greater than 1")
    @Max(value = 4, message = "Level less then 4")
    private Integer level;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be grater than 18")
    @Max(value = 30, message = "Age must be less than 30")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;
    private LocalDateTime createdAt;
}
