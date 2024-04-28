package dk.kea.dat3js.hogwarts5.config;

import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentRepository;
import dk.kea.dat3js.hogwarts5.teachers.Teacher;
import dk.kea.dat3js.hogwarts5.teachers.TeacherRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import dk.kea.dat3js.hogwarts5.house.House;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InitData implements CommandLineRunner {

    private final HouseRepository houseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;




    public InitData(HouseRepository houseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.houseRepository = houseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
      //clearData();
        createHouses();
        createStudents();
        createTeachers();
    }

//    public void clearData() {
//        studentRepository.deleteAll();
//        studentRepository.flush();
//        teacherRepository.deleteAll();
//        teacherRepository.flush();
//        houseRepository.deleteAll();
//        houseRepository.flush();
//
//
//
//    }

    private void createStudents() {
        // To avoid creating and re-creating the same students, we first get all those that already exist
        List<Student> existingStudents = new ArrayList<>();
//    Set<Student> existingStudents = new HashSet<>();
//     existingStudents.addAll(studentRepository.findAll());

        Student harry = new Student("Harry", "James", "Potter", gryffindor, 5, false, "male");
        Student hermione = new Student("Hermione", "Jean", "Granger", gryffindor, 5, false, "female");
        Student ron = new Student("Ron", "Bilius", "Weasley", gryffindor, 5, false, "male");
        Student neville = new Student("Neville", "Frank", "Longbottom", gryffindor, 5, false, "male");
        Student ginny = new Student("Ginny", "Molly", "Weasley", gryffindor, 5, true, "female");
        Student fred = new Student("Fred", "Gideon", "Weasley", gryffindor, 5, false, "male");
        Student george = new Student("George", "Fabian", "Weasley", gryffindor, 5, false, "male");
        Student percy = new Student("Percy", "Ignatius", "Weasley", gryffindor, 5, true, "male");

        Student draco = new Student("Draco", null, "Malfoy", slytherin, 5, false, "male");
        Student cedric = new Student("Cedric", null, "Diggory", hufflepuff, 6, false, "male");
        Student luna = new Student("Luna", null, "Lovegood", ravenclaw, 4, false, "female");

        //studentRepository.deleteAll();
//      studentRepository.save(harry);
//studentRepository.save(hermione);
//studentRepository.save(ron);
//studentRepository.save(neville);
//studentRepository.save(ginny);
//studentRepository.save(fred);
//studentRepository.save(george);
//studentRepository.save(percy);
//studentRepository.save(draco);
//studentRepository.save(cedric);
//studentRepository.save(luna);



        existingStudents.addAll(List.of(harry, hermione, ron, neville, ginny, fred, george, percy, draco, cedric, luna));

        studentRepository.saveAll(existingStudents);
    }

    private void createTeachers() {
        // To avoid creating and re-creating the same teachers, we first get all those that already exist
        List<Teacher> newTeachers = new ArrayList<>();
//    Set<Teacher> existingTeachers = new HashSet<>();
//    existingTeachers.addAll(teacherRepository.findAll());

        Teacher severus = new Teacher("Severus", "Prince", "Snape", slytherin, "Potions", LocalDate.of(1981, 11, 1));
        Teacher minerva = new Teacher("Minerva", null, "McGonagall", gryffindor, "Transfiguration", LocalDate.of(1956, 12, 1));
        Teacher filius = new Teacher("Filius", null, "Flitwick", ravenclaw, "Charms", LocalDate.of(1975, 9, 1));
        Teacher pomona = new Teacher("Pomona", null, "Sprout", hufflepuff, "Herbology", LocalDate.of(1975, 9, 1));
        Teacher sybill = new Teacher("Sybill", "Cassandra", "Trelawney", ravenclaw, "Divination", LocalDate.of(1979, 9, 1));
        Teacher alastor = new Teacher("Alastor", "Mad-Eye", "Moody", gryffindor, "Defence Against the Dark Arts", LocalDate.of(1994, 9, 1));

        //teacherRepository.deleteAll();
        newTeachers.addAll(List.of(severus, minerva, filius, pomona, sybill, alastor));
        teacherRepository.saveAll(newTeachers);
    }

    private House gryffindor;
    private House slytherin;
    private House hufflepuff;
    private House ravenclaw;


    private void createHouses() {

        List<House> newHouses = new ArrayList<>();
//    Set<House> existingHouses = new HashSet<>();
//    existingHouses.addAll(houseRepository.findAll());

        gryffindor = new House("Gryffindor", "Godric Gryffindor", new String[]{"red", "gold"});
        slytherin = new House("Slytherin", "Salazar Slytherin", new String[]{"green", "silver"});
        hufflepuff = new House("Hufflepuff", "Helga Hufflepuff", new String[]{"yellow", "black"});
        ravenclaw = new House("Ravenclaw", "Rowena Ravenclaw", new String[]{"blue", "bronze"});

        newHouses.addAll(List.of(gryffindor, slytherin, hufflepuff, ravenclaw));

        //houseRepository.deleteAll();
        houseRepository.saveAll(newHouses);

        //existingHouses.addAll(List.of(gryffindor, slytherin, hufflepuff, ravenclaw));
        //houseRepository.saveAll(existingHouses);
//     houseRepository.save(gryffindor);
//      houseRepository.save(slytherin);
//      houseRepository.save(hufflepuff);
//      houseRepository.save(ravenclaw);


    }
}
