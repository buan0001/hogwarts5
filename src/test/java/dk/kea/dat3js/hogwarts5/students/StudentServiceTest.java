package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
class StudentServiceTest {
    List<Student> students;
    @MockBean
    private HouseRepository houseRepository;

    @Autowired
    private StudentService studentService;


    public void setupMockHouses() {
        when(houseRepository.findById("Gryffindor")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Gryffindor","Godric Gryffindor", new String[]{"red", "gold"})));
        when(houseRepository.findById("Ravenclaw")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Ravenclaw","Rowena Ravenclaw", new String[]{"blue", "bronze"})));
        when(houseRepository.findById("Hufflepuff")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Hufflepuff","Helga Hufflepuff", new String[]{"yellow", "black"})));
        when(houseRepository.findById("Slytherin")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Slytherin","Salazar Slytherin", new String[]{"green", "silver"})));

    }
    @BeforeEach
    void setUp() {
        if (students == null) {
            return;
        }
        setupMockHouses();
        students.add(new Student("Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 1, false, "male"));
        students.add(new Student("Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 1, false, "female"));
        students.add(new Student("Ron", "Bilius", "Weasley", houseRepository.findById("Gryffindor").get(), 1, false, "male"));
        students.add(new Student("Draco", null, "Malfoy", houseRepository.findById("Slytherin").get(), 1, false, "male"));
    }

    @Test
    void togglePrefectWithNonPrefectAndTwoPrefectsAlreadyInHouse() {

        //arrange

        //act

        //assert
    }
}