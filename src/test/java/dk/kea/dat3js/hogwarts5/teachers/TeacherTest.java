package dk.kea.dat3js.hogwarts5.teachers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void getFullNameWithMiddleName() {
        //arrange
        Teacher teacher = new Teacher("first middle last", null, null, null);
        String expected = "First Middle Last";

        //act
        String actual = teacher.getFullName();

        //assert
        assertEquals(expected, actual);

    }

    @Test
    void getFullNameWithNullMiddleName() {
        //arrange
        Teacher teacher = new Teacher("Harry",null, "James", null, null, null);
        String expected = "Harry Potter";

        //act
        String actual = teacher.getFullName();

        //assert
        assertEquals(expected, actual);

    }

    @Test
    void getFullNameWithEmptyMiddleName() {
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "Harry Potter";

        //act
        String actual = teacher.getFullName();

        //assert
        assertEquals(expected, actual);

    }

    @Test
    void setFullNameWithSingleMiddleName() {
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "Harry James Potter";

        //act
        teacher.setFullName("Harry James Potter");


        //assert
        assertEquals("Harry", teacher.getFirstName());
        assertEquals("James", teacher.getMiddleName());
        assertEquals("Potter", teacher.getLastName());

    }

    @Test
    void setFullNameWithNullMiddleName() {
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "Harry Potter";

        //act
        teacher.setFullName("Harry Potter");


        //assert
        assertEquals("Harry", teacher.getFirstName());
        assertNull(teacher.getMiddleName());
        assertEquals("Potter", teacher.getLastName());

    }

    @Test
    void setFullNameWithoutLastName() {
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "Harry";

        //act
        teacher.setFullName("Harry");


        //assert
        assertEquals("Harry", teacher.getFirstName());
        assertNull(teacher.getMiddleName());
        assertNull(teacher.getLastName());

    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);

        //act
        teacher.setFullName("Albus Percival Wulfric Brian Dumbledore");

        //assert
        assertEquals("Albus", teacher.getFirstName());
        assertEquals("Percival Wulfric Brian", teacher.getMiddleName());
        assertEquals("Dumbledore", teacher.getLastName());
    }

    @Test
    void setFullNameWithSpaceAsMiddleName() {
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);

        //act
        teacher.setFullName("Albus     Dumbledore");

        //assert
        assertEquals("Albus", teacher.getFirstName());
        assertNull(teacher.getMiddleName());
        assertEquals("Dumbledore", teacher.getLastName());
    }
    @Test
    void setFullnameWithEmptyString(){
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "first middle last";

        //act
        teacher.setFullName("");

        //assert
        assertEquals("first middle last", teacher.getFullName());
    }

    @Test
    void setFullNameWithMultipleEmptyStrings(){
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "first middle last";

        //act
        teacher.setFullName("   ");

        //assert
        assertEquals("first middle last", teacher.getFullName());
    }

    @Test
    void setFullnameWithNull(){
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "first middle last";

        //act
        teacher.setFullName(null);

        //assert
        assertEquals("first middle last", teacher.getFullName());
    }

    @Test
    void capitalizeIndividualNamesWithMultipleMiddleNames(){
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "First Middle Last";

        //act
        teacher.setFullName("first middle1 middle2 middle3 last");

        //assert
        assertEquals("First", teacher.getFirstName());
        assertEquals("Middle1 Middle2 Middle3", teacher.getMiddleName());
        assertEquals("Last", teacher.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCharacters(){
        //arrange
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "First Middle Last";

        //act
        teacher.setFullName("fIrSt mIdDlE lAsT");

        //assert
        assertEquals(expected, teacher.getFullName());
    }

    @Test
    void createTeacherFromFullname(){
        //arrange & act
        Teacher teacher = new Teacher("Harry", "James", null, null, null);
        String expected = "First Middle1 Middle2 Last";

        //assert
        assertEquals(expected, teacher.getFullName());
    }

}