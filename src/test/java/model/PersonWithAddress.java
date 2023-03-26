package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xlsx.serializer.annotation.OmitField;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonWithAddress {
    private String firstName;
    private String lastname;
    private LocalDate birthDate;
    @OmitField
    private Address address;


}
