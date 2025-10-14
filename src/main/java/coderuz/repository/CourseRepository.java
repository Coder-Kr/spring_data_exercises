package coderuz.repository;

import coderuz.entity.CourseEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {

    List<CourseEntity> findByName(String name);

    List<CourseEntity> findByPrice(Double price);

    List<CourseEntity> findByDuration(Integer duration);

    List<CourseEntity> findByPriceBetween(Double price1, Double price2);

    List<CourseEntity> findByCreatedAtBetween(LocalDateTime createdAt1, LocalDateTime createdAt2);

    @Query("from CourseEntity c where  c.id=:id")
    CourseEntity findById(@Param("id") int id);

    @Query("from CourseEntity ")
    List<CourseEntity> findAllQ();

    @Modifying
    @Transactional
    @Query("update CourseEntity c set c.name=:name, c.duration=:duration where c.id=:id")
    void updateByIdQ(@Param("id") Integer id, @Param("name") String name, @Param("duration") Integer duration);

    @Modifying
    @Transactional
    @Query("delete CourseEntity where id=:id")
    void deleteByIdQ(@Param("id") int id);

    @Query("from CourseEntity where name=:name")
    List<CourseEntity> findByNameQ(String name);

    @Query("from CourseEntity where price between :fromPrice and :toPrice")
    List<CourseEntity> findByPriceQ(@Param("fromPrice") Double fromPrice, @Param("toPrice") Double toPrice);

    @Query("from CourseEntity where createdAt between :fromDate and :toDate")
    List<CourseEntity> findByDates(@Param("fromDate") String fromDate, @Param("toDate") String toDate);


    //========Pagination========
    Page<CourseEntity> findAll(Pageable pageable);

    Page<CourseEntity> findByPrice(Double price, Pageable pageable);

    Page<CourseEntity> findByPriceBetween(Double priceFrom, Double priceTo, Pageable pageable);


}
