package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.exceptions.BadRequestException;
import dk.kea.dat3js.hogwarts5.exceptions.StudentNotFoundException;
import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/prefects")
@RestController
public class PrefectController {

    private final PrefectService prefectService;

    public PrefectController(PrefectService prefectService) {
        this.prefectService = prefectService;
    }

    @GetMapping
    public List<StudentResponseDTO> getAllPrefects(){
        return prefectService.getAllPrefects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getPrefect(@PathVariable int id){
        try {
            return ResponseEntity.ok(prefectService.getPrefect(id));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/house/{house}")
    public List<StudentResponseDTO> getPrefectsByHouse(@PathVariable String house){
        return prefectService.getPrefectsByHouse(house);
    }

    @PostMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> promoteToPrefect(@PathVariable int id){
        try {
            return ResponseEntity.ok(prefectService.promoteToPrefect(id));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> removePrefect(@PathVariable int id){
        try {
            return ResponseEntity.ok(prefectService.removePrefect(id));
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
        // return ResponseEntity.of(prefectService.removePrefect(id));
    }

}
