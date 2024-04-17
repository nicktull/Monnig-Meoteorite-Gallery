package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto.SampleHistoryDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SampleHistoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SampleHistoryService sampleHistoryService;

    @Autowired
    ObjectMapper objectMapper;

    List<SampleHistory> sampleHistoryList;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp(){
        this.sampleHistoryList = new ArrayList<>();

        SampleHistory s1 = new SampleHistory();
        s1.setId("123456");
        s1.setSampleDate("12/12/2024");
        s1.setSampleCategory("Meow");
        s1.setSampleNotes("A cat makes the meow sound.");

        SampleHistory s2 = new SampleHistory();
        s2.setId("654321");
        s2.setSampleCategory("Created");
        s2.setSampleNotes("Meteor was created.");

    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void testSaveSampleHistorySuccess() throws Exception {
        //Given
        SampleHistoryDto sampleHistoryDto = new SampleHistoryDto(
                null,
                "12/12/2025",
                "Created",
                "Created new meteor.",
                null);

        String json = this.objectMapper.writeValueAsString(sampleHistoryDto);

        SampleHistory savedEntry = new SampleHistory();
        savedEntry.setId("123456");
        savedEntry.setSampleDate("12/12/2025");
        savedEntry.setSampleCategory("Created");
        savedEntry.setSampleNotes("Created new meteor.");

        given(this.sampleHistoryService.save(Mockito.any(SampleHistory.class))).willReturn(savedEntry);

        //When and Then
        this.mockMvc.perform(post(this.baseUrl+"/samplehistory").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add sample history success."))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.sampleDate").value(savedEntry.getSampleDate()))
                .andExpect(jsonPath("$.data.sampleCategory").value(savedEntry.getSampleCategory()))
                .andExpect(jsonPath("$.data.sampleNotes").value(savedEntry.getSampleNotes()));
    }

    @Test
    void testDeleteSampleHistorySuccess() throws Exception{
        doNothing().when(this.sampleHistoryService).delete("123456");

        this.mockMvc.perform(delete(this.baseUrl+ "/samplehistory/123456").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSampleHistoryFail()throws Exception{
        //given
        doThrow(new ObjectNotFoundException("sample history", "123456")).when(this.sampleHistoryService).delete("123456");

        // When and Then
        this.mockMvc.perform(delete(this.baseUrl+ "/samplehistory/123456").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find sample history with Id 123456"))
                .andExpect(jsonPath("$.data").isEmpty());
    }



}
