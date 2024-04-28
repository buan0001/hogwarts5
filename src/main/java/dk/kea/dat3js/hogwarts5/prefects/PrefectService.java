package dk.kea.dat3js.hogwarts5.prefects;

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

    public Optional<StudentResponseDTO> getPrefect(int id) {
        Optional<Student> foundStudent = studentRepository.findById(id);

        if (foundStudent.isPresent() && foundStudent.get().isPrefect()) {
            return Optional.ofNullable(studentService.toDTO(foundStudent.get()));
        } else {
            return Optional.empty();
        }
    }


    public List<StudentResponseDTO> getPrefectsByHouse(String house) {
        return studentRepository.findAllByHouseNameAndPrefectIsTrue(house).stream().map(studentService::toDTO).toList();
    }

    public Optional<StudentResponseDTO> removePrefect(int id) {
        Optional<Student> foundStudent = studentRepository.findById(id);
        if (foundStudent.isPresent()) {
            Student student = foundStudent.get();
            if (student.isPrefect()) {
                student.setPrefect(false);
                return Optional.of(studentService.toDTO(studentRepository.save(student)) );
            }
        }
        return Optional.empty();
    }

    public Optional<StudentResponseDTO> promoteToPrefect(Student prefect) {
        Optional<Student> student = studentRepository.findById(prefect.getId());
        if (student.isPresent()) {
            Student studentToUpdate = student.get();
            if (!studentToUpdate.isPrefect() && isPrefectChangeAllowed(studentToUpdate)) {
                studentToUpdate.setPrefect(true);
                return Optional.of(studentService.toDTO(studentRepository.save(studentToUpdate)) );
            }
        }
            return Optional.empty();
    }

    public boolean isPrefectChangeAllowed(Student student) {
        System.out.println("Checking if prefect change is allowed for" + student.getFullName() + " in house " + student.getHouse().getName() + " as a " + student.getGender() + " in year " + student.getSchoolYear() + "th year.");
        System.out.println("Student's prefect status: " + student.isPrefect());
        if (!student.isPrefect()) {
            System.out.println("Student is not a prefect but will attempt to become one");
            // Check if the student is at or above year 5
            if (student.getSchoolYear() < 5) {
                System.out.println("Student is not at year 5 - is instead " + student.getSchoolYear() + "th year");
                return false;
            }

            // Check if house already has two prefects - if the student to update is not already a prefect they will attempt to become one, which must fail
            List<Student> prefectsInHouse = studentRepository.findAllByHouseNameAndPrefectIsTrue(student.getHouse().getName());
            System.out.println("Prefects in house: " + prefectsInHouse.size());

            if (prefectsInHouse.size() == 2) {
                System.out.println("House already has two prefects");
                return false;
            }

            // If there's already a prefect in the house of the same gender, also deny the request
            boolean sameGenderPrefectExists = prefectsInHouse.stream().anyMatch(prefect -> prefect.getGender().equalsIgnoreCase(student.getGender()));
            if (sameGenderPrefectExists) {
                System.out.println("House already has a prefect of that gender");
                return false;
            }
        }
        System.out.println("Prefect change allowed");
        return true;
    }
}
