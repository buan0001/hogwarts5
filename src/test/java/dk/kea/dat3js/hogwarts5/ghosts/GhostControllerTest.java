package dk.kea.dat3js.hogwarts5.ghosts;

import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GhostController.class)
@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {HouseService.class})
class GhostControllerTest {

    @MockBean
    private HouseRepository houseRepository;

    @Autowired
    private MockMvc mockMvc;

public void setupMockHouses() {
    when(houseRepository.findById("Gryffindor")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Gryffindor","Godric Gryffindor", new String[]{"red", "gold"})));
    when(houseRepository.findById("Ravenclaw")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Ravenclaw","Rowena Ravenclaw", new String[]{"blue", "bronze"})));
    when(houseRepository.findById("Hufflepuff")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Hufflepuff","Helga Hufflepuff", new String[]{"yellow", "black"})));
    when(houseRepository.findById("Slytherin")).thenReturn(java.util.Optional.of(new dk.kea.dat3js.hogwarts5.house.House("Slytherin","Salazar Slytherin", new String[]{"green", "silver"})));

}

    @Test
    void getAllGhosts() throws Exception {
        setupMockHouses();


                mockMvc.perform(get("/ghosts"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isArray())
                        .andExpect(jsonPath("$[0].name").value("Nearly Headless Nick"))
                        .andExpect(jsonPath("$[1].name").value("The Grey Lady"))
                        .andExpect(jsonPath("$[2].name").value("The Fat Friar"))
                        .andExpect(jsonPath("$[3].name").value("The Bloody Baron"));

    }

    @Test
    void getGhost() {
        // arrange
        setupMockHouses();
        GhostController ghostController = new GhostController(new HouseService(houseRepository));
        // act
        var ghost = ghostController.getGhost("Nearly Headless Nick");
        // assert

        assertTrue(ghost.hasBody());
        assertNotNull(ghost);
        assertEquals("Nearly Headless Nick", ghost.getBody().getName());
        assertEquals("Sir Nicholas de Mimsy-Porpington", ghost.getBody().getRealName());
        assertEquals("Gryffindor", ghost.getBody().getHouse().getName());
    }
}