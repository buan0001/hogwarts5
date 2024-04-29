package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.anyInt;
import static reactor.core.publisher.Mono.when;

//@WebMvcTest(StudentService.class)
public class MonoStudentTest {

    // Fire essentielle begreber: Mono og Flux samt Publisher og Subscriber
    // Mono og Flux er Reactors primære typer, der repræsenterer en asynkron strøm af data.
    // REACTOR CORE har lavet standarden for Reactive Streams, som er en API til asynkron strømning af data.
    // Mono er en Publisher, der udsender 0 eller 1 element, mens Flux er en Publisher, der udsender 0 til mange elementer.
    // En Subscriber abonnerer på en Publisher og modtager elementer fra den.
    // En Subscriber kan reagere på elementer, fejl og afslutning af en strøm.
    // En subscriber tilknyttes en publisher ved at kalde dens subscribe-metode.


    @Test
    public void givenMonoPublisher_whenSubscribeThenReturnSingleValue() {
        Mono<String> helloMono = Mono.just("Hello");
        helloMono.subscribe(
                value -> System.out.println(value),
                error -> System.err.println(error),
                () -> System.out.println("Done")
        );
        helloMono.flux().subscribe(
                value -> System.out.println(value),
                error -> System.err.println(error),
                () -> System.out.println("Done")
        );

        StepVerifier.create(helloMono)
                .expectNext("Hello")
                .expectComplete()
                .verify();
    }

    @Test
    public void givenFluxPublisher_whenSubscribedThenReturnMultipleValues() {
        Flux<String> stringFlux = Flux.just("{'id':'Hello'}", "Baeldung");
        StepVerifier.create(stringFlux)
                .expectNext("{'id':'Hello'}")
                .expectNext("Baeldung")
                .expectComplete()
                .verify();
    }

    @Test
    public void givenFluxPublisher_whenSubscribeThenReturnMultipleValuesWithError() {
        Flux<String> stringFlux = Flux.just("Hello", "Baeldung", "Error")
                .map(str -> {
                    if (str.equals("Error"))
                        throw new RuntimeException("Throwing Error");
                    return str;
                });
        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("Baeldung")
                .expectError()
                .verify();
    }









    //    @MockBean
//    private StudentRepository studentRepository;
//    @MockBean
//    private HouseRepository houseRepository;
//    public void setupMockHouses() {
//        when(houseRepository.findById("Gryffindor")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Gryffindor", "Godric Gryffindor", new String[]{"red", "gold"})));
//        when(houseRepository.findById("Ravenclaw")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Ravenclaw", "Rowena Ravenclaw", new String[]{"blue", "bronze"})));
//        when(houseRepository.findById("Hufflepuff")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Hufflepuff", "Helga Hufflepuff", new String[]{"yellow", "black"})));
//        when(houseRepository.findById("Slytherin")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Slytherin", "Salazar Slytherin", new String[]{"green", "silver"})));
//    }
//    List<Student> students = new ArrayList<>();
//


//    @BeforeEach
//    void setUp() {
//        setupMockHouses();
//
//        if (!students.isEmpty()) {
//            students.clear();
//        }
//        students.add(new Student("Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 1, false, "f"));
//        students.add(new Student("Ron", "Bilius", "Weasley", houseRepository.findById("Gryffindor").get(), 1, false, "m"));
//
//        when(studentRepository.findById(anyInt())).thenAnswer(i -> {
//            int id = i.getArgument(0);
//            return students.stream().filter(s -> s.getId() == id).findFirst();
//        });
//    }

}
