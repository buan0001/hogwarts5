package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void getFullNameWithMiddleName() {
        //arrange
        Student student = new Student("Harry", "James", "Potter", null, 1);
        String expected = "Harry James Potter";

        //act
        String actual = student.getFullName();

        //assert
        assertEquals(expected, actual);

    }

    @Test
    void getFullNameWithNullMiddleName() {
        //arrange
        Student student = new Student("Harry", null, "Potter", null, 1);
        String expected = "Harry Potter";

        //act
        String actual = student.getFullName();

        //assert
        assertEquals(expected, actual);

    }

    @Test
    void getFullNameWithEmptyMiddleName() {
        //arrange
        Student student = new Student("Harry", "", "Potter", null, 1);
        String expected = "Harry Potter";

        //act
        String actual = student.getFullName();

        //assert
        assertEquals(expected, actual);

    }

    @Test
    void setFullNameWithSingleMiddleName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);

        //act
        student.setFullName("Harry James Potter");


        //assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());

    }

    @Test
    void setFullNameWithNullMiddleName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);

        //act
        student.setFullName("Harry Potter");


        //assert
        assertEquals("Harry", student.getFirstName());
        assertNull(student.getMiddleName());
        assertEquals("Potter", student.getLastName());

    }

    @Test
    void setFullNameWithoutLastName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);
        String expected = "Harry";

        //act
        student.setFullName("Harry");


        //assert
        assertEquals("Harry", student.getFirstName());
        assertNull(student.getMiddleName());
        assertNull(student.getLastName());

    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);

        //act
        student.setFullName("Albus Percival Wulfric Brian Dumbledore");

        //assert
        assertEquals("Albus", student.getFirstName());
        assertEquals("Percival Wulfric Brian", student.getMiddleName());
        assertEquals("Dumbledore", student.getLastName());
    }

    @Test
    void setFullNameWithSpaceAsMiddleName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);

        //act
        student.setFullName("Albus     Dumbledore");

        //assert
        assertEquals("Albus", student.getFirstName());
        assertNull(student.getMiddleName());
        assertEquals("Dumbledore", student.getLastName());
    }
    @Test
    void setFullnameWithEmptyString(){
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);
        String expected = "first middle last";

        //act
        student.setFullName("");

        //assert
        assertEquals("first middle last", student.getFullName());
    }

    @Test
    void setFullNameWithMultipleEmptyStrings(){
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);
        String expected = "first middle last";

        //act
        student.setFullName("   ");

        //assert
        assertEquals("first middle last", student.getFullName());
    }

    @Test
    void setFullnameWithNull(){
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);
        String expected = "first middle last";

        //act
        student.setFullName(null);

        //assert
        assertEquals("first middle last", student.getFullName());
    }

    @Test
    void capitalizeIndividualNamesWithMultipleMiddleNames(){
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);
        String expected = "First Middle Last";

        //act
        student.setFullName("first middle1 middle2 middle3 last");

        //assert
        assertEquals("First", student.getFirstName());
        assertEquals("Middle1 Middle2 Middle3", student.getMiddleName());
        assertEquals("Last", student.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCharacters(){
        //arrange
        Student student = new Student("first", "middle", "last", null, 1);
        String expected = "First Middle Last";

        //act
        student.setFullName("fIrSt mIdDlE lAsT");

        //assert
        assertEquals(expected, student.getFullName());
    }

    @Test
    void createStudentFromFullname(){
        //arrange & act
        Student student = new Student("first middle1 middle2 last", null, 1);
        String expected = "First Middle1 Middle2 Last";

        //assert
        assertEquals(expected, student.getFullName());
    }

}