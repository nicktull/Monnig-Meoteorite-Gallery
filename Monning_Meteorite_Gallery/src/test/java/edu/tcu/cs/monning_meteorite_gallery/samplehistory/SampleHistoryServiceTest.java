package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleHistoryServiceTest {

    @Mock
    SampleHistoryRepository sampleHistoryRepository;

    @InjectMocks
    SampleHistoryService sampleHistoryService;

    List<SampleHistory> sampleHistoryList;

    @BeforeEach
    void setUp() {

        SampleHistory s1 = new SampleHistory();
        s1.setSampleHistoryId(123456);
        s1.setSampleCategory("Meow");
        s1.setSampleNotes("A cat makes the meow sound.");

        this.sampleHistoryList = new ArrayList<>();
        this.sampleHistoryList.add(s1);

    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void testFindById(){
        SampleHistory s1 = new SampleHistory();
        s1.setSampleHistoryId(123456);
        s1.setSampleDate("12/12/2025");
        s1.setSampleCategory("Created");
        s1.setSampleNotes("Create a new meteor.");

        Meteorite m = new Meteorite();
        m.setMonnigNumber("M398.1");
        m.setName("Nick's Meteor");

        s1.setMeteor(m);

        given(this.sampleHistoryRepository.findById("123456")).willReturn(Optional.of(s1));

        SampleHistory returnedSampleHistory = this.sampleHistoryService.findById(123456);

        assertThat(returnedSampleHistory.getSampleHistoryId()).isEqualTo(s1.getSampleHistoryId());
        assertThat(returnedSampleHistory.getSampleDate()).isEqualTo(s1.getSampleDate());
        assertThat(returnedSampleHistory.getSampleCategory()).isEqualTo(s1.getSampleCategory());
        assertThat(returnedSampleHistory.getSampleNotes()).isEqualTo(s1.getSampleNotes());
        verify(this.sampleHistoryRepository,times(1)).findById("123456");
    }

    @Test
    void testFindByIdNotFound(){
        given(this.sampleHistoryRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            SampleHistory returnedSampleHistory = this.sampleHistoryService.findById(123456);
        });

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find sample history with Id 123456");
        verify(this.sampleHistoryRepository,times(1)).findById("123456");

    }


    @Test
    void testAddSuccess(){
        //Given
        SampleHistory newEntry = new SampleHistory();
        newEntry.setSampleHistoryId(1);
        newEntry.setSampleCategory("Created");
        newEntry.setSampleNotes("This meteor is from space.");

        given(this.sampleHistoryRepository.save(newEntry)).willReturn(newEntry);

        //When
        SampleHistory savedEntry = this.sampleHistoryService.save(newEntry);

        //Then
        assertThat(savedEntry.getSampleHistoryId()).isEqualTo(1);
        assertThat(savedEntry.getSampleCategory()).isEqualTo(newEntry.getSampleCategory());
        assertThat(savedEntry.getSampleNotes()).isEqualTo(newEntry.getSampleNotes());
        verify(this.sampleHistoryRepository, times(1)).save(newEntry);
    }

    @Test
    void testDeleteSucces(){
        //Given
        SampleHistory history = new SampleHistory();
        history.setSampleHistoryId(123456);
        history.setSampleDate("12/12/2025");
        history.setSampleCategory("Cut");
        history.setSampleNotes("We are cutting this meteorite now.");

        given(this.sampleHistoryRepository.findById("123456")).willReturn(Optional.of(history));
        doNothing().when(this.sampleHistoryRepository).deleteById("123456");

        //When
        this.sampleHistoryService.delete(123456);

        //Then
        verify(this.sampleHistoryRepository, times(1)).deleteById("123456");

    }

    @Test
    void testDeleteNotFound(){
        //Given
        given(this.sampleHistoryRepository.findById("123456")).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.sampleHistoryService.delete(123456);
        });

        //Then
        verify(this.sampleHistoryRepository,times(1)).findById("123456");
    }


}
