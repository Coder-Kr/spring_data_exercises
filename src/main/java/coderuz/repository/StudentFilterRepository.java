package coderuz.repository;
import coderuz.dto.FilterResultDTO;
import coderuz.dto.StudentDTO;
import coderuz.entity.StudentEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
            query.append(" and s.name like :name");
            paramsMap.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if(filter.getSurname() != null) {
            query.append(" and s.surname like :surname");
            paramsMap.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getAge() != null) {
            query.append(" and s.age = :age");
            paramsMap.put("age", filter.getAge());
        }
        if(filter.getGender() != null) {
            query.append(" and s.gender = :gender");
            paramsMap.put("gender", filter.getGender());
        }
        if(filter.getCreatedAt() != null) {
            query.append(" and s.createdAt = :createdAt");
            paramsMap.put("createdAt", filter.getCreatedAt());
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
}
