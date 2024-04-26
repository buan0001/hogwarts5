package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrefectService {

    private final StudentRepository studentRepository;

    public PrefectService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllPrefects() {
        return studentRepository.findAllByPrefectIsTrue();
    }

    public Optional<Student> getPrefect(int id) {
        Optional<Student> foundStudent = studentRepository.findById(id);

        if (foundStudent.isPresent() && foundStudent.get().isPrefect()) {
            return foundStudent;
        } else {
            return Optional.empty();
        }
    }


    public List<Student> getPrefectsByHouse(String house) {
        return studentRepository.findAllByHouseNameAndPrefectIsTrue(house);
    }

    public Optional<Student> removePrefect(int id) {
        Optional<Student> foundStudent = studentRepository.findById(id);

        if (foundStudent.isPresent()){
            Student student = foundStudent.get();
            if (student.isPrefect()) {
                student.setPrefect(false);
                studentRepository.save(student);
                return foundStudent;
            }
        }
        return Optional.empty();
    }

    public Student promoteToPrefect(Student prefect) {
        if (!prefect.isPrefect()) {
            prefect.setPrefect(true);
            return studentRepository.save(prefect);
        } else {
            return prefect;
        }
    }
}
