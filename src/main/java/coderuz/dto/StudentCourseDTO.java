package coderuz.dto;

import coderuz.entity.CourseEntity;
import coderuz.entity.StudentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private StudentDTO student;
    private CourseDTO course;
}
