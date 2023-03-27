package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithArrayTypeField {

    private String firstName;
    private String lastname;
    private String[] attributes;

}
