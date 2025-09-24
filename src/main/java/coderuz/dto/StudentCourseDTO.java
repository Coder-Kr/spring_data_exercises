package coderuz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class StudentCourseDTO {
    private Integer id;

    @NotNull(message = "StudentId required.")
    private Integer studentId;

    @NotNull(message = "CourseId required.")
    private Integer courseId;

    @NotNull(message = "Mark required.")
    private Integer mark;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
