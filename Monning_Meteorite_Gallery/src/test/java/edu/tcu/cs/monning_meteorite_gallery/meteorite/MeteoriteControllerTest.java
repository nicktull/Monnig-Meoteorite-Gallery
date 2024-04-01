package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MeteoriteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MeteoriteService meteoriteService;

    List<Meteorite> meteorites;

    @BeforeEach
    void setUp() {
        this.meteorites = new ArrayList<>();

        Meteorite Abott = new Meteorite();
        Abott.setName("Abott");
        Abott.setMonnigNumber("M398.1");
        Abott.setCountry("USA");
        Abott.setMClass("Ordinary Chondrite");
        Abott.setMGroup("H");
        Abott.setYearFound("1951");
        Abott.setWeight("325.1");
        this.meteorites.add(Abott);

        Meteorite Abee = new Meteorite();
        Abee.setName("Abee");
        Abee.setMonnigNumber("M499.2");
        Abee.setCountry("Canada");
        Abee.setMClass("Enstatite Chondrite");
        Abee.setMGroup("EH");
        Abee.setYearFound("1952");
        Abee.setWeight("785.9");
        this.meteorites.add(Abee);

        Meteorite Abernathy = new Meteorite();
        Abernathy.setName("Abernathy");
        Abernathy.setMonnigNumber("M239.1.1");
        Abernathy.setCountry("USA");
        Abernathy.setMClass("Ordinary Chondrite");
        Abernathy.setMGroup("L");
        Abernathy.setYearFound("1941");
        Abernathy.setWeight("453.1");
        this.meteorites.add(Abernathy);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testfindArtifactByIdSuccess() throws Exception {
        // Given
        System.out.println("Mocking findByID method...");
        given(this.meteoriteService.findByID("M398.1")).willReturn(this.meteorites.get(0));
        System.out.println("Mocking completed.");

        System.out.println("Performing request...");
        // When and Then
        this.mockMvc.perform(get("/api/v1/meteorites/M398.1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Found"))
                .andExpect(jsonPath("$.data.MonnigNumber").value("M398.1"));
        //http://localhost:8080//api/v1/meteorites/M398.1
        System.out.println("Request completed.");
    }

    @Test
    void testfindMeteoriteByIdFail() throws Exception{
        // Given

        // When and Then
    }


    @Test
    void testAddMeteoriteSuccess() throws Exception{
        // Given

        // When

        // Then
    }

    @Test
    void testAddMeteoriteFail() throws Exception{
        // Given

        // When and Then
    }

    @Test
    void testDeleteMeteoriteSuccess() throws Exception {
        //Given

        //When

        //Then
    }

    @Test
    void testDeleteMeteoriteFail() throws Exception {
        // Given

        // When and Then
    }

}