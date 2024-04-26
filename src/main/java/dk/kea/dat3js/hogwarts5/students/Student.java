package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.common.PersonWithNames;
import dk.kea.dat3js.hogwarts5.house.House;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Student implements PersonWithNames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    @ManyToOne
    private House house;
    private Integer schoolYear; // 1-7

    private boolean prefect;
     private String gender;


    public Student() {
    }

    public Student(String firstName, String lastName, House house, int schoolYear) {
        this(firstName, null, lastName, house, schoolYear);
    }

    public Student(String fullName, House house, int schoolYear) {
        this.setFullName(fullName);
        this.house = house;
        this.schoolYear = schoolYear;
    }

    public Student(String firstName, String middleName, String lastName, House house, int schoolYear) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.house = house;
        this.schoolYear = schoolYear;
    }
    public Student(String firstName, String middleName, String lastName, House house, int schoolYear, boolean prefect, String gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.house = house;
        this.schoolYear = schoolYear;
        this.prefect = prefect;
        this.gender =gender;
    }



        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getMiddleName(), student.getMiddleName()) && Objects.equals(getLastName(), student.getLastName()) && Objects.equals(getHouse().getName(), student.getHouse().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getMiddleName(), getLastName(), getHouse().getName());
    }


}
