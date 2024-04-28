package dk.kea.dat3js.hogwarts5.prefects;

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
        return ResponseEntity.of(prefectService.getPrefect(id));
    }

    @GetMapping("/house/{house}")
    public List<StudentResponseDTO> getPrefectsByHouse(@PathVariable String house){
        return prefectService.getPrefectsByHouse(house);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> promoteToPrefect(@RequestBody Student prefect){
        return ResponseEntity.of(prefectService.promoteToPrefect(prefect)) ;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> removePrefect(@PathVariable int id){
        return ResponseEntity.of(prefectService.removePrefect(id));
    }

}
