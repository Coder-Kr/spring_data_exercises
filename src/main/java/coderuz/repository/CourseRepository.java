package coderuz.repository;

import coderuz.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {

    List<CourseEntity> findByName(String name);

    List<CourseEntity> findByPrice(Double price);

    List<CourseEntity> findByDuration(Double duration);

}
