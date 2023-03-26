package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xlsx.serializer.annotation.XLSXProperty;

@Data
@AllArgsConstructor
public class PersonWithAnnotation {

    @XLSXProperty(name = "imię")
    private String firstName;

    @XLSXProperty(name = "nazwisko")
    private String lastname;
}
