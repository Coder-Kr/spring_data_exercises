package coderuz.repository;

import coderuz.dto.FilterResultDTO;
import coderuz.dto.StudentDTO;
import coderuz.entity.StudentEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentFilterRepository {

    @Autowired
    EntityManager entityManager;

    public FilterResultDTO<StudentEntity> filter(StudentDTO filter, int page, int size) {
        StringBuilder query = new StringBuilder(" where s.age>10");
        Map<String, Object> paramsMap = new HashMap<>();

        if (filter.getId() != null) {
            query.append(" and s.id=:id");
            paramsMap.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.append(" and s.name ilike :name");
            paramsMap.put("name", "%" + filter.getName() + "%");
        }
        if (filter.getSurname() != null) {
            query.append(" and s.surname ilike :surname");
            paramsMap.put("surname", "%" + filter.getSurname() + "%");
        }
        if (filter.getAge() != null) {
            query.append(" and s.age = :age");
            paramsMap.put("age", filter.getAge());
        }
        if (filter.getGender() != null) {
            query.append(" and s.gender = :gender");
            paramsMap.put("gender", filter.getGender());
        }
        if(filter.getLevel() != null){
            query.append(" and s.level = :level");
            paramsMap.put("level", filter.getLevel());
        }
        if (filter.getCreatedAt() != null) {
            query.append(" and s.createdAt >= :createdAtFrom and s.createdAt <= :createdAtTo");
            var from = LocalDateTime.of(filter.getCreatedAt(), LocalTime.MIN);
            var to = LocalDateTime.of(filter.getCreatedAt(), LocalTime.MAX);
            paramsMap.put("createdAtFrom", from);
            paramsMap.put("createdAtTo", to);
        }

        StringBuilder selectBuilder = new StringBuilder("select s from StudentEntity s");
        selectBuilder.append(query);
        selectBuilder.append(" order by s.createdAt desc");

        StringBuilder countBuilder = new StringBuilder("select count(s) from StudentEntity s");
        countBuilder.append(query);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString(), StudentEntity.class);
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }

        //yuqoridagi for() bilan bir xil logic
        //paramsMap.forEach(selectQuery::setParameter);

        selectQuery.setFirstResult(page * size); //offset $skip
        selectQuery.setMaxResults(size); //$limit

        List<StudentEntity> content = selectQuery.getResultList();
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        paramsMap.forEach(countQuery::setParameter);
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<StudentEntity>(content, totalCount);
    }

    public FilterResultDTO<StudentEntity> filterNative(StudentDTO filter, int page, int size) {
        System.out.println("filterNative>>>>>" +filter.getName());
        StringBuilder query = new StringBuilder(" where s.age>10");
        Map<String, Object> paramsMap = new HashMap<>();

        if (filter.getId() != null) {
            query.append(" and s.id=:id");
            paramsMap.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.append(" and s.name ilike :name");
            paramsMap.put("name", "%" + filter.getName() + "%");
        }
        if (filter.getSurname() != null) {
            query.append(" and s.surname ilike :surname");
            paramsMap.put("surname", "%" + filter.getSurname() + "%");
        }
        if (filter.getAge() != null) {
            query.append(" and s.age = :age");
            paramsMap.put("age", filter.getAge());
        }
        if (filter.getGender() != null) {
            query.append(" and s.gender = :gender");
            paramsMap.put("gender", filter.getGender().name());
        }
        if(filter.getLevel() != null){
            query.append(" and s.level = :level");
            paramsMap.put("level", filter.getLevel());
        }
        if (filter.getCreatedAt() != null) {
            query.append(" and s.created_at = :createdAt");
            paramsMap.put("createdAt", filter.getCreatedAt());
        }

        StringBuilder selectBuilder = new StringBuilder("select * from student s");
        selectBuilder.append(query);
        selectBuilder.append(" order by s.created_at desc");

        StringBuilder countBuilder = new StringBuilder("select count(*) from student s");
        countBuilder.append(query);

        Query selectQuery = entityManager.createNativeQuery(selectBuilder.toString(), StudentEntity.class);
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }

        //yuqoridagi for() bilan bir xil logic
        //paramsMap.forEach(selectQuery::setParameter);

        selectQuery.setFirstResult(page * size); //offset $skip
        selectQuery.setMaxResults(size); //$limit

        List<StudentEntity> content = selectQuery.getResultList();
        System.out.println("content>>>>>"+content);
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());
        paramsMap.forEach(countQuery::setParameter);
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<StudentEntity>(content, totalCount);
    }
}
