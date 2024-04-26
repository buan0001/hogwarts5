package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.students.Student;
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
    public List<Student> getAllPrefects(){
        return prefectService.getAllPrefects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getPrefect(@PathVariable int id){
        return ResponseEntity.of(prefectService.getPrefect(id));
    }

    @GetMapping("/house/{house}")
    public List<Student> getPrefectsByHouse(@PathVariable String house){
        return prefectService.getPrefectsByHouse(house);
    }

    @PostMapping
    public ResponseEntity<Student> promoteToPrefect(@RequestBody Student prefect){
        return prefectService.promoteToPrefect(prefect);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Student> removePrefect(@PathVariable int id){
        return ResponseEntity.of(prefectService.removePrefect(id));
    }

}
