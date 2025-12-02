package coderuz.repository;

import coderuz.entity.StudentEntity;
import coderuz.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class StudentEntityIntegrationTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void testStudentEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails() {
        //Arrange
        StudentEntity student = new StudentEntity();
        student.setName("John");
        student.setSurname("Doe");
        student.setLevel(2);
        student.setAge(24);
        student.setGender(Gender.MALE);

        //Act
        StudentEntity savedStudent = testEntityManager.persistAndFlush(student);

        //Assert
        Assertions.assertTrue(savedStudent.getId() > 0);
        Assertions.assertEquals(student.getName(), savedStudent.getName(), "name should be the same");
        Assertions.assertEquals(student.getSurname(), savedStudent.getSurname(), "surname should be the same");
        Assertions.assertEquals(student.getLevel(), savedStudent.getLevel(), "level should be the same");
        Assertions.assertEquals(student.getAge(), savedStudent.getAge(), "age should be the same");
        Assertions.assertEquals(student.getGender(), savedStudent.getGender(), "gender should be the same");
    }
}
