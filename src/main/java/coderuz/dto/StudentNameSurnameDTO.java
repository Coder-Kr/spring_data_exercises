package coderuz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentNameSurnameDTO {
    private String name;
    private String surname;

    public StudentNameSurnameDTO(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
}
