import lombok.SneakyThrows;
import model.Address;
import model.Person;
import model.PersonWithAddress;
import model.PersonWithAnnotation;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.xlsx.serializer.XLSXSerializer;
import utils.Utils;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CreatingXLSXFileTest {
    @SneakyThrows
    @Test
    public void shouldCreateFile() {

        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            people.add(new Person(Utils.drawFirstName(), Utils.drawLastName()));
        }
        Workbook workbook = XLSXSerializer.serialize(people, Person.class);
        FileOutputStream fileOutputStream = new FileOutputStream("test1.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
        assertTrue(Files.exists(Paths.get("test1.xlsx")));
    }

    @SneakyThrows
    @Test
    public void shouldCreateFileWithAnnotatedFields() {
        List<PersonWithAnnotation> people = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            people.add(new PersonWithAnnotation(Utils.drawFirstName(), Utils.drawLastName()));
        }
        Workbook workbook = XLSXSerializer.serialize(people, PersonWithAnnotation.class);
        FileOutputStream fileOutputStream = new FileOutputStream("test2.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
        assertTrue(Files.exists(Paths.get("test2.xlsx")));
    }

    @SneakyThrows
    @Test
    public void shouldCreateFileWithOmittedField() {
        List<PersonWithAddress> people = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            people.add(new PersonWithAddress(Utils.drawFirstName(), Utils.drawLastName(), Utils.drawBirthDate(),
                    new Address("LA", Utils.drawStreet(), Integer.toString(Utils.range(1, 10)), Integer.toString(Utils.range(1, 10)))));
        }
        Workbook workbook = XLSXSerializer.serialize(people, PersonWithAddress.class);
        FileOutputStream fileOutputStream = new FileOutputStream("test3.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
        assertTrue(Files.exists(Paths.get("test3.xlsx")));
    }

    @SneakyThrows
    @Test
    public void shouldCreateFileWithSelectedField() {
        List<PersonWithAddress> people = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            people.add(new PersonWithAddress(Utils.drawFirstName(), Utils.drawLastName(), Utils.drawBirthDate(),
                    new Address("LA", Utils.drawStreet(), Integer.toString(Utils.range(1, 10)), Integer.toString(Utils.range(1, 10)))));
        }
        Workbook workbook = XLSXSerializer.serialize(people, PersonWithAddress.class, Arrays.asList("lastname", "firstName"));
        FileOutputStream fileOutputStream = new FileOutputStream("test4.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
        assertTrue(Files.exists(Paths.get("test4.xlsx")));
    }
}
