package coderuz.repository;

import coderuz.entity.StudentCourseEntity;
import coderuz.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudetnCourseRepository extends CrudRepository<StudentCourseEntity, Integer> {
    Optional<StudentCourseEntity> findByStudentIdAndCourseId(Integer studentId, Integer courseId);

    List<StudentCourseEntity> findByStudentIdAndCreatedAtBetween(Integer studentId, LocalDateTime fromDate, LocalDateTime toDate);

    List<StudentCourseEntity> findByStudentIdOrderByCreatedAtDesc(Integer studentId);

    List<StudentCourseEntity> findByStudentIdAndCourseIdOrderByCreatedAtDesc(Integer studentId, Integer courseId);

    Optional<StudentCourseEntity> findTopByStudentIdOrderByCreatedAtDesc(Integer studentId);

    List<StudentCourseEntity> findTop3ByStudentIdOrderByCreatedAtDesc(Integer studentId);

    Optional<StudentCourseEntity> findTopByStudentIdOrderByCreatedAtAsc(Integer studentId);

    Optional<StudentCourseEntity> findByStudentIdAndCourseIdOrderByCreatedAtAsc(Integer studentId, Integer courseId);

    Optional<StudentCourseEntity> findTopByStudentIdAndCourseIdOrderByMarkDesc(Integer studentId, Integer courseId);

    List<StudentCourseEntity> findByStudentId(Integer studentId);

    StudentCourseEntity findTopByStudentId(Integer studentId);

    long countByStudentIdAndMarkGreaterThan(Integer studentId, Integer mark);

    Optional<StudentCourseEntity> findFirstByCourseIdOrderByMarkDesc(Integer courseId);

    long countByCourseId(Integer courseId);


}
