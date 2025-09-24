package coderuz.repository;

import coderuz.entity.StudentCourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudetnCourseRepository extends CrudRepository<StudentCourseEntity,Integer> {
    Optional<StudentCourseEntity> findByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
