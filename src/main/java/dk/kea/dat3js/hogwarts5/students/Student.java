package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.House;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    @ManyToOne
    private House house;
    private Integer schoolYear; // 1-7

    public Student() {
    }

    public Student(String firstName, String lastName, House house, int schoolYear) {
        this(firstName, null, lastName, house, schoolYear);
    }

    public Student(String firstName, String middleName, String lastName, House house, int schoolYear) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.house = house;
        this.schoolYear = schoolYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getFullName() {
        return firstName + " " + (middleName != null && !middleName.isEmpty() ? middleName + " " : "") + lastName;
    }

    public String setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            System.out.println("Name cannot be empty or null");
            return "Name cannot be empty or null";
        }
        String[] names = fullName.split(" ");
        // remove empty strings
        names = Arrays.stream(names).filter(s -> !s.isEmpty()).toArray(String[]::new);
        if (names.length < 1) {
            System.out.println("Name must contain at least 1 part");
            return "Name must contain at least 1 part";
        }

        firstName = capitalize( names[0]);
        if (names.length > 2) {
            // middleName = names[1];
            StringBuilder correctMiddle = new StringBuilder();
            for (int i = 1; i < names.length-1; i++) {
                correctMiddle.append(capitalize(names[i])).append(" ");
            }
            middleName = correctMiddle.toString().trim();
            lastName = capitalize(names[names.length - 1]) ;
        } else if (names.length > 1) {
            middleName = null;
            lastName = capitalize(names[1]) ;
        } else {
            middleName = null;
            lastName = null;
        }
        System.out.println(getMiddleName());
        return getFullName();
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

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
