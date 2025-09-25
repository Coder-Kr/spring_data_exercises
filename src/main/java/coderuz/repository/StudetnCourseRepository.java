package coderuz.repository;

import coderuz.entity.StudentCourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudetnCourseRepository extends CrudRepository<StudentCourseEntity,Integer> {
    Optional<StudentCourseEntity> findByStudentIdAndCourseId(Integer studentId, Integer courseId);

    List<StudentCourseEntity> findByStudentIdAndCreatedAtBetween(Integer studentId, LocalDateTime fromDate, LocalDateTime toDate);

    List<StudentCourseEntity> findByStudentIdOrderByCreatedAtDesc(Integer studentId);
}
