package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.exceptions.BadRequestException;
import dk.kea.dat3js.hogwarts5.exceptions.StudentNotFoundException;
import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentRepository;
import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import dk.kea.dat3js.hogwarts5.students.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrefectService {


    private final StudentRepository studentRepository;

    private final StudentService studentService;


    public PrefectService(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    public List<StudentResponseDTO> getAllPrefects() {
        return studentRepository.findAllByPrefectIsTrue().stream().map(studentService::toDTO).toList();
    }

    public StudentResponseDTO getPrefect(int id) {
        System.out.println("Amount of students: " + studentRepository.count());
        System.out.println("Amount of prefects: " + studentRepository.countByPrefectIsTrue());
        studentRepository.findAll().forEach(student -> System.out.println("name: " + student.getFullName() + "prefect status: " + student.isPrefect() + " id: " + student.getId()));
        Optional<Student> foundStudent = studentRepository.findById(id);

        if (foundStudent.isPresent()) {
            if ( foundStudent.get().isPrefect()) {
                return studentService.toDTO(foundStudent.get());
            }
            else {
                System.out.println("Student is not a prefect " + foundStudent.get().getFullName() + " " + foundStudent.get().isPrefect());
                throw new BadRequestException("Student is not a prefect");
            }
        } else {

            throw new StudentNotFoundException("Student not found");
        }
    }


    public List<StudentResponseDTO> getPrefectsByHouse(String house) {
        return studentRepository.findAllByHouseNameAndPrefectIsTrue(house).stream().map(studentService::toDTO).toList();
    }

    public StudentResponseDTO removePrefect(int id) {
        Optional<Student> foundStudent = studentRepository.findById(id);
        if (foundStudent.isPresent()) {
            Student student = foundStudent.get();
            if (student.isPrefect()) {
                student.setPrefect(false);
                return studentService.toDTO(studentRepository.save(student) );
            }
            System.out.println("Student is not a prefect " + student.getFullName() + " " + student.isPrefect());
            throw new BadRequestException("Student is not a prefect");
        }
        throw new StudentNotFoundException("Student not found");
    }

    public StudentResponseDTO promoteToPrefect(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            Student studentToUpdate = student.get();
            if (!studentToUpdate.isPrefect() && studentService.isPrefectChangeAllowed(studentToUpdate)) {
                studentToUpdate.setPrefect(true);
                return studentService.toDTO(studentRepository.save(studentToUpdate) );
            }
            System.out.println("Student is already a prefect or does not meet the requirements");
            throw new BadRequestException("Student is not a prefect");
        }
        System.out.println("No student found");
        throw new StudentNotFoundException("Student not found");
    }

}
