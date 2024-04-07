package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MeteoriteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MeteoriteService meteoriteService;

    @Autowired
    ObjectMapper objectMapper;

    List<Meteorite> meteorites;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

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
        given(this.meteoriteService.findByID("M398.1")).willReturn(this.meteorites.get(0));

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/meteorites/M398.1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Found"))
                .andExpect(jsonPath("$.data.MonnigNumber").value("M398.1"));
    }

    @Test
    void testfindMeteoriteByIdNotFound() throws Exception{
        // Given
        given(this.meteoriteService.findByID("M398.1")).willThrow(new ObjectNotFoundException("meteorite", "M398.1"));

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/meteorites/M398.1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find meteorite with id M398.1"))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    @Test
    void testAddMeteoriteSuccess() throws Exception{
        // Given
        MeteoriteDto meteoriteDto = new MeteoriteDto(
                "M33.1",
                "Nick's Meteor",
                "United States",
                "Nicks",
                "Web Tech",
                "2024",
                "500",
                null
                );

        String json = this.objectMapper.writeValueAsString(meteoriteDto);

        Meteorite savedMeteorite = new Meteorite();
        savedMeteorite.setMonnigNumber("M33.1");
        savedMeteorite.setName("Nick's Meteor");
        savedMeteorite.setCountry("United States");
        savedMeteorite.setMClass("Nicks");
        savedMeteorite.setMGroup("Web Tech");
        savedMeteorite.setYearFound("2024");
        savedMeteorite.setWeight("500");

        given(this.meteoriteService.save(Mockito.any(Meteorite.class))).willReturn(savedMeteorite);

        // When and Then
        this.mockMvc.perform(post(this.baseUrl + "/meteorites/").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.MonnigNumber").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedMeteorite.getName()))
                .andExpect(jsonPath("$.data.country").value(savedMeteorite.getCountry()))
                .andExpect(jsonPath("$.data.class").value(savedMeteorite.getMClass()))
                .andExpect(jsonPath("$.data.group").value(savedMeteorite.getMGroup()))
                .andExpect(jsonPath("$.data.year").value(savedMeteorite.getYearFound()))
                .andExpect(jsonPath("$.data.weight").value(savedMeteorite.getWeight()));

    }

    @Test
    void testDeleteMeteoriteSuccess() throws Exception {
        //Given
        doNothing().when(this.meteoriteService).delete("M398.1");

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/meteorites/M398.1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteMeteoriteFail() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("meteorite", "M398.1")).when(this.meteoriteService).delete("M398.1");

        // When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/meteorites/M398.1").accept(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.flag").value(false))
                  .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                  .andExpect(jsonPath("$.message").value("Could not find meteorite with Id M398.1 :("))
                  .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testUpdateMeteoriteSuccess() throws Exception{
        // Given
        MeteoriteDto meteoriteDto = new MeteoriteDto(
                "M398.1",
                "Abott",
                "USA",
                "Ordinary Chrondite",
                "H",
                "1951",
                "325.1",
                null);
        String json = this.objectMapper.writeValueAsString(meteoriteDto);

        Meteorite updatedMeteorite = new Meteorite();
        updatedMeteorite.setMonnigNumber("M398.1");
        updatedMeteorite.setName("Abott");
        updatedMeteorite.setCountry("Usa");
        updatedMeteorite.setMClass("Ordinary Chrondite");
        updatedMeteorite.setMGroup("H");
        updatedMeteorite.setYearFound("1951");
        updatedMeteorite.setWeight("325.1");

        given(this.meteoriteService.update(eq("M398.1"), Mockito.any(Meteorite.class))).willReturn(updatedMeteorite);

        //When and Then
        this.mockMvc.perform(put(this.baseUrl + "/meteorites/M398.1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.MonnigNumber").value("M398.1"))
                .andExpect(jsonPath("$.data.Name").value(updatedMeteorite.getName()))
                .andExpect(jsonPath("$.data.Country").value(updatedMeteorite.getCountry()))
                .andExpect(jsonPath("$.data.MClass").value(updatedMeteorite.getMClass()))
                .andExpect(jsonPath("$.data.MGroup").value(updatedMeteorite.getMGroup()))
                .andExpect(jsonPath("$.data.yearFound").value(updatedMeteorite.getYearFound()))
                .andExpect(jsonPath("$.data.weight").value(updatedMeteorite.getWeight()));
    }

    @Test
    void testUpdateMeteoriteErrorWithNonExistentId() throws Exception {
        // Given
        MeteoriteDto meteoriteDto = new MeteoriteDto(
                "M398.1",
                "Abott",
                "USA",
                "Ordinary Chrondite",
                "H",
                "1951",
                "325.1",
                null);
        String json = this.objectMapper.writeValueAsString(meteoriteDto);

        given(this.meteoriteService.update(eq("M398.1"), Mockito.any(Meteorite.class))).willThrow(new ObjectNotFoundException("meteorite", "M398.1"));

        //When and Then
        this.mockMvc.perform(put(this.baseUrl + "/meteorites/M398.1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find meteorite with id M398.1"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}