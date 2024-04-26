package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.HouseService;
import dk.kea.dat3js.hogwarts5.prefects.PrefectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final HouseService houseService;

    private final PrefectService prefectService;

    public StudentService(StudentRepository studentRepository, HouseService houseService, PrefectService prefectService) {
        this.studentRepository = studentRepository;
        this.houseService = houseService;
        this.prefectService = prefectService;
    }

    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<StudentResponseDTO> findById(int id) {
        return studentRepository.findById(id).map(this::toDTO);
    }

    public StudentResponseDTO save(StudentRequestDTO student) {
        return toDTO(studentRepository.save(fromDTO(student)));
    }

    public Optional<StudentResponseDTO> updateIfExists(int id, StudentRequestDTO student) {
        if (studentRepository.existsById(id)) {
            Student existingStudent = fromDTO(student);
            existingStudent.setId(id);
            return Optional.of(toDTO(studentRepository.save(existingStudent)));
        } else {
            // TODO: Throw error?
            return Optional.empty();
        }
    }

    public Optional<StudentResponseDTO> partialUpdate(int id, StudentRequestDTO student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student studentToUpdate = existingStudent.get();
            if (student.firstName() != null) {
                studentToUpdate.setFirstName(student.firstName());
            }
            if (student.middleName() != null) {
                studentToUpdate.setMiddleName(student.middleName());
            }
            if (student.lastName() != null) {
                studentToUpdate.setLastName(student.lastName());
            }
            if (student.house() != null) {
                studentToUpdate.setHouse(houseService.findById(student.house()).orElseThrow());
            }
            if (student.schoolYear() != null) {
                studentToUpdate.setSchoolYear(student.schoolYear());
            }
            return Optional.of(toDTO(studentRepository.save(studentToUpdate)));
        } else {
            // TODO: handle error
            return Optional.empty();
        }
    }

    public Optional<StudentResponseDTO> deleteById(int id) {
        Optional<StudentResponseDTO> existingStudent = studentRepository.findById(id).map(this::toDTO);
        studentRepository.deleteById(id);
        return existingStudent;
    }

    private StudentResponseDTO toDTO(Student studentEntity) {
        StudentResponseDTO dto = new StudentResponseDTO(
                studentEntity.getId(),
                studentEntity.getFirstName(),
                studentEntity.getMiddleName(),
                studentEntity.getLastName(),
                studentEntity.getFullName(),
                studentEntity.getHouse().getName(),
                studentEntity.getSchoolYear(),
                studentEntity.isPrefect(),
                studentEntity.getGender()
        );

        return dto;
    }

    private Student fromDTO(StudentRequestDTO studentDTO) {
        Student entity = new Student(
                studentDTO.firstName(),
                studentDTO.middleName(),
                studentDTO.lastName(),
                houseService.findById(studentDTO.house()).orElseThrow(),
                studentDTO.schoolYear(),
                studentDTO.prefect(),
                studentDTO.gender()
        );

        if (studentDTO.fullName() != null) {
            entity.setFullName(studentDTO.fullName());
        }

        return entity;
    }

    public Optional<StudentResponseDTO> togglePrefect(int id) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student studentToUpdate = existingStudent.get();
            //boolean isPrefectAllowed = isPrefectChangeAllowed(studentToUpdate);
            boolean isPrefectAllowed = prefectService.isPrefectChangeAllowed(studentToUpdate);
            if (!studentToUpdate.isPrefect() && !isPrefectAllowed) {
                System.out.println("Prefect change not allowed");
                return Optional.empty();
            }

            studentToUpdate.setPrefect(!studentToUpdate.isPrefect());
            return Optional.of(toDTO(studentRepository.save(studentToUpdate)));


        } else {
            System.out.println("Student not found");
            return Optional.empty();
        }
    }
    public boolean isPrefectChangeAllowed(Student student) {
        if (!student.isPrefect()) {
            System.out.println("Student is not a prefect but will attempt to become one");
            // Check if the student is at or above year 5
            if (student.getSchoolYear() < 5) {
                System.out.println("Student is not at or above year 5");
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
