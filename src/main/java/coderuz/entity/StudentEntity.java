package coderuz.entity;

import coderuz.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity //db da automatic table hosil qiladi
@Getter
@Setter
@Table(name="student")
public class StudentEntity {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //serial
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name="level")
    private Integer level;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
