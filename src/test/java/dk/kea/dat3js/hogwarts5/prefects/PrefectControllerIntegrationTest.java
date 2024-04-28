package dk.kea.dat3js.hogwarts5.prefects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PrefectControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void notNull(){
        assertNotNull(webClient);
    }


    @Test
    void getAllPrefects() {
        webClient.get().uri("/prefects").exchange().expectStatus().isOk();
    }

    @Test
    void getPrefect() {
        webClient.get().uri("/prefects/5").exchange().expectStatus().isOk().expectBody().json("""
                {
                
                    "firstName": "Percy",
                    "middleName": "Ignatius",
                    "lastName": "Weasley",
                    "fullName": "Percy Ignatius Weasley",
                    "house": "Gryffindor",
                    "schoolYear": 5
                }
                """);
    }

    @Test
    void getPrefectsByHouse() {
        webClient.get().uri("/prefects/house/Gryffindor").exchange().expectStatus().isOk().expectBody().jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void promoteToPrefectWithAllChecksPassing() {
        webClient.post().uri("/prefects").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                {
                "id": 9
                }
                """)
                .exchange().expectStatus().isOk().expectBody().json("""
                {
                    "id": 9,
                    "firstName": "Draco",
                    "middleName": null,
                    "lastName": "Malfoy",
                    "fullName": "Draco Malfoy",
                    "house": "Slytherin"
                }
                """);
    }

    @Test
    void promoteToPrefectWithStudentNotAtYear5() {
        webClient.post().uri("/prefects").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                {
                "id": 1
                }
                """)
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    void promoteToPrefectWhenStudentIsAlreadyPrefect() {
        webClient.post().uri("/prefects").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                {
                "id": 5
                }
                """)
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    void promoteToPrefectWhenAlreadyAnotherPrefectOfSameGenderFromSameHouse() {
        webClient.post().uri("/prefects").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                {
                "id": 6
                }
                """)
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    void removePrefect() {
    }
}