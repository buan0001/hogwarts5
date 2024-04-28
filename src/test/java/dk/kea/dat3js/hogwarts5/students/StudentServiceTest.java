package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.ghosts.GhostController;
import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import dk.kea.dat3js.hogwarts5.prefects.PrefectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")

@WebMvcTest(StudentService.class)
class StudentServiceTest {
    List<Student> students = new ArrayList<>();
    @MockBean
    private HouseRepository houseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private HouseService houseService;

    @BeforeEach
    void setUp() {

        setupMockHouses();
        if (!students.isEmpty()) {
            students.clear();
        }

//        students.add(new Student("Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 1, false, "female"));
//        students.add(new Student("Ron", "Bilius", "Weasley", houseRepository.findById("Gryffindor").get(), 1, false, "male"));
//        students.add(new Student("Draco", null, "Malfoy", houseRepository.findById("Slytherin").get(), 1, false, "male"));

        when(studentRepository.findById(anyInt())).thenAnswer(i -> {
            int id = i.getArgument(0);
            return students.stream().filter(s -> s.getId() == id).findFirst();
        });

        when(studentRepository.findAllByHouseNameAndPrefectIsTrue(any())).thenAnswer(i -> {
            String houseName = i.getArgument(0);
            return students.stream().filter(s -> s.getHouse().getName().equals(houseName) && s.isPrefect()).toList();
        });

        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);

//        when(prefectService.isPrefectChangeAllowed(any(Student.class))).thenAnswer(i -> {
//            Student student = i.getArgument(0);
//            System.out.println("Student's prefect status: " + student.isPrefect());
//            if (!student.isPrefect()) {
//                System.out.println("Student is not a prefect but will attempt to become one");
//                // Check if the student is at or above year 5
//                if (student.getSchoolYear() < 5) {
//                    System.out.println("Student is not at or above year 5");
//                    return false;
//                }
//
//                // Check if house already has two prefects - if the student to update is not already a prefect they will attempt to become one, which must fail
//                List<Student> prefectsInHouse = studentRepository.findAllByHouseNameAndPrefectIsTrue(student.getHouse().getName());
//                System.out.println("Prefects in house: " + prefectsInHouse.size());
//
//                if (prefectsInHouse.size() == 2) {
//                    System.out.println("House already has two prefects");
//                    return false;
//                }
//
//                // If there's already a prefect in the house of the same gender, also deny the request
//                boolean sameGenderPrefectExists = prefectsInHouse.stream().anyMatch(prefect -> prefect.getGender().equalsIgnoreCase(student.getGender()));
//                if (sameGenderPrefectExists) {
//                    System.out.println("House already has a prefect of that gender");
//                    return false;
//                }
//            }
//            System.out.println("Prefect change allowed");
//            return true;
//        });
    }

    public void setupMockHouses() {
        when(houseRepository.findById("Gryffindor")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Gryffindor", "Godric Gryffindor", new String[]{"red", "gold"})));
        when(houseRepository.findById("Ravenclaw")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Ravenclaw", "Rowena Ravenclaw", new String[]{"blue", "bronze"})));
        when(houseRepository.findById("Hufflepuff")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Hufflepuff", "Helga Hufflepuff", new String[]{"yellow", "black"})));
        when(houseRepository.findById("Slytherin")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Slytherin", "Salazar Slytherin", new String[]{"green", "silver"})));
    }


    @Test
    void togglePrefectToTrueWithNoOtherPrefectsAndYearFive() {

        Student student = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, false, "male");
        students.add(student);
        StudentService studentService = new StudentService(studentRepository, houseService);

        // Act
        Optional<StudentResponseDTO> updatedStudent = studentService.togglePrefect(1);
        if (updatedStudent.isPresent()) {
            System.out.println(updatedStudent.get());
        }

        // Assert
        assertTrue(updatedStudent.isPresent());
        assertTrue(student.isPrefect());
        //assertTrue(updatedStudent.get().prefect());
    }

    @Test
    void togglePrefectToTrueWithNoOtherPrefectsAndYearOne() {

        Student student = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 1, false, "male");
        students.add(student);
        StudentService studentService = new StudentService(studentRepository, houseService);

        // Act
        Optional<StudentResponseDTO> updatedStudent = studentService.togglePrefect(1);
        if (updatedStudent.isPresent()) {
            System.out.println(updatedStudent.get());
        }


        // Assert
        assertTrue(updatedStudent.isEmpty());
        assertFalse(student.isPrefect());
    }

    @Test
    void togglePrefectToTrueWithTwoOtherPrefectsFromSameHouseAndCorrectYear() {
        // Arrange
        Student student1 = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male");
        Student student2 = new Student(2, "Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 5, true, "female");
        Student student3 = new Student(3, "Ron", "Bilius", "Weasley", houseRepository.findById("Gryffindor").get(), 5, false, "male");
        students.addAll(List.of(student1, student2, student3));

        StudentService studentService = new StudentService(studentRepository, houseService);

        // act
        Optional<StudentResponseDTO> updatedStudent = studentService.togglePrefect(3);

        // assert
        assertTrue(updatedStudent.isEmpty());
        assertFalse(student3.isPrefect());
    }

    @Test
    void togglePrefectToTrueWithOneOtherPrefectFromSameHouseWithSameGender() {
        // Arrange
        Student student1 = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male");
        Student student3 = new Student(2, "Ron", "Bilius", "Weasley", houseRepository.findById("Gryffindor").get(), 5, false, "male");
        students.addAll(List.of(student1, student3));

        StudentService studentService = new StudentService(studentRepository, houseService);

        // Act
        Optional<StudentResponseDTO> updatedStudent = studentService.togglePrefect(2);

        // Assert
        assertFalse(updatedStudent.isPresent());
        assertFalse(student3.isPrefect());
    }

    @Test
    void togglePrefectToTrueWithOneOtherPrefectFromSameHouseWithDifferentGender() {
        // Arrange
        Student student1 = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, false, "male");
        Student student2 = new Student(2, "Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 5, true, "female");
        students.addAll(List.of(student1, student2));

        StudentService studentService = new StudentService(studentRepository, houseService);

        // Act

        Optional<StudentResponseDTO> updatedStudent = studentService.togglePrefect(1);
        if (updatedStudent.isPresent()) {
            System.out.println(updatedStudent.get());
        }

        // Assert
        assertTrue(updatedStudent.isPresent());
        assertTrue(student1.isPrefect());
    }

}