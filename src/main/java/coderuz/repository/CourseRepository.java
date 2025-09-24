package coderuz.repository;

import coderuz.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {

    List<CourseEntity> findByName(String name);

    List<CourseEntity> findByPrice(Double price);

    List<CourseEntity> findByDuration(Integer duration);

    List<CourseEntity> findByPriceBetween(Double price1, Double price2);

    List<CourseEntity> findByCreatedAtBetween(LocalDateTime createdAt1, LocalDateTime createdAt2);

}
