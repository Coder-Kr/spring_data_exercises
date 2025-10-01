package coderuz.mapper;

import java.time.LocalDateTime;

public interface StudentDetailInfoMapper {
    Integer getId();
    Integer getStudentId();
    String getStudentName();
    String getStudentSurname();
    Integer getCourseId();
    String getCourseName();
    Integer getMark();
    LocalDateTime getCreatedDate();
}
