package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.config.InitData;
import dk.kea.dat3js.hogwarts5.exceptions.BadRequestException;
import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrefectControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

//    @Autowired
//    private InitData initData;
//
////    @Autowired
////    private EntityManager entityManager;
//
//    @BeforeEach
//    @Transactional
//    void setUp() throws Exception {
//       // resetIncrement();
//        initData.run();
//    }

//protected void resetIncrement() {
//        entityManager.createNativeQuery("ALTER SEQUENCE students_SEQ RESTART WITH 1").executeUpdate();
//        entityManager.createNativeQuery("ALTER SEQUENCE teachers_SEQ RESTART WITH 1").executeUpdate();
//        entityManager.createNativeQuery("ALTER SEQUENCE houses_SEQ RESTART WITH 1").executeUpdate();
//    }

//
//    @AfterEach
//    void tearDown() throws Exception {
//        initData.run();
//    }

    @Test
    @Order(1)
    void notNull(){
        assertNotNull(webClient);
    }




    @Test
    @Order(2)
    void getAllPrefects() {
        webClient.get().uri("/prefects").exchange().expectStatus().isOk();
        //webClient.get().uri("/prefects").exchange().expectStatus().isOk().expectBody().jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    @Order(3)
    void getPrefect() {

        webClient.get().uri("/prefects/8").exchange().expectStatus().isOk().expectBody().json("""
                {
                
                    "firstName": "Percy",
                    "middleName": "Ignatius",
                    "lastName": "Weasley",
                    "fullName": "Percy Ignatius Weasley",
                    "house": "Gryffindor",
                    "schoolYear": 5,
                    "prefect": true
                }
                """);
    }

    @Test
    @Order(4)
    void getPrefectsByHouse() {
        webClient.get().uri("/prefects/house/Gryffindor").exchange().expectStatus().isOk();
        //webClient.get().uri("/prefects/house/Gryffindor").exchange().expectStatus().isOk().expectBody().jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    @Order(5)
    void promoteToPrefectWithAllChecksPassing() {
        webClient.post().uri("/prefects/9")
                .exchange().expectStatus().isOk().expectBody().json("""
                {
                    
                    "firstName": "Draco",
                    "middleName": null,
                    "lastName": "Malfoy",
                    "fullName": "Draco Malfoy",
                    "house": "Slytherin",
                    "prefect": true
                }
                """);
    }

    @Test
    @Order(6)
    void promoteToPrefectWithStudentNotAtYear5() {
        webClient.post().uri("/prefects/11")
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    @Order(7)
    void promoteToPrefectWhenStudentIsAlreadyPrefect() {
        webClient.post().uri("/prefects/8")
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    @Order(8)
    void promoteToPrefectWhenAlreadyAnotherPrefectOfSameGenderFromSameHouse() {
        webClient.post().uri("/prefects/1")
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    @Order(9)
    void promoteToPrefectWithInvalidId() {
        webClient.post().uri("/prefects/100")
                .exchange().expectStatus().isNotFound();
    }

    @Test
    @Order(10)
    void removePrefectFromExistingPrefect() {
        webClient.delete().uri("/prefects/5")
                .exchange().expectStatus().isOk().expectBody().json("""
                {
                    "firstName": "Ginny",
                    "middleName": "Molly",
                    "lastName": "Weasley",
                    "fullName": "Ginny Molly Weasley",
                    "house": "Gryffindor",
                    "schoolYear": 5,
                    "prefect": false,
                    "gender": "female"
            
                }
                """);
    }

    @Test
    @Order(11)
    void removePrefectFromNonPrefect() {
        webClient.delete().uri("/prefects/11")
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    @Order(12)
    void removePrefectWithInvalidId() {
        webClient.delete().uri("/prefects/100")
                .exchange().expectStatus().isNotFound();
    }
}