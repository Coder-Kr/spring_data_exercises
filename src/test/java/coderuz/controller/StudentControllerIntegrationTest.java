package coderuz.controller;

import coderuz.dto.StudentDTO;
import coderuz.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidDetailsProvided_returnCreatedUser(){

        //Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Jasurbek");
        studentDTO.setSurname("Odilov");
        studentDTO.setLevel(2);
        studentDTO.setAge(28);
        studentDTO.setGender(Gender.MALE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<StudentDTO> request = new HttpEntity<>(studentDTO,headers);

        //Act
        ResponseEntity<StudentDTO> createUserResponse = this.testRestTemplate.postForEntity("/student/create", request, StudentDTO.class);

        StudentDTO createdUserDetails = createUserResponse.getBody();

        //Assert
        Assertions.assertEquals(HttpStatus.OK, createUserResponse.getStatusCode());
        Assertions.assertNotNull(createdUserDetails);
        Assertions.assertEquals(studentDTO.getName(), createdUserDetails.getName());


    }
}
