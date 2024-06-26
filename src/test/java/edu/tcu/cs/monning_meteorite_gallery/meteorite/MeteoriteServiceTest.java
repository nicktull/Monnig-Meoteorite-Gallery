package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.utils.IdWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MeteoriteServiceTest {

    @Mock //Mocks behavior of meteorite repository, will not use real mRepo
    MeteoriteRepository meteoriteRepository;

    @InjectMocks //mRepo will be injected into this
    MeteoriteService meteoriteService;
    @Mock
    private IdWorker idWorker;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIDSuccess() {
        // Given
        Meteorite m = new Meteorite();

        m.setName("Abott");
        m.setMonnigNumber("M398.1");
        m.setCountry("USA");
        m.setMClass("Ordinary Chondrite");
        m.setMGroup("H");
        m.setYearFound("1951");
        m.setWeight("325.1");

        given(meteoriteRepository.findById("M398.1")).willReturn(Optional.of(m));

        // When
        Meteorite returnedMeteorite = meteoriteService.findByID("M398.1");

        // Then
        assertThat(returnedMeteorite.getMonnigNumber()).isEqualTo(m.getMonnigNumber());
        assertThat(returnedMeteorite.getName()).isEqualTo(m.getName());
        assertThat(returnedMeteorite.getCountry()).isEqualTo(m.getCountry());
        assertThat(returnedMeteorite.getMClass()).isEqualTo(m.getMClass());
        assertThat(returnedMeteorite.getMGroup()).isEqualTo(m.getMGroup());
        assertThat(returnedMeteorite.getYearFound()).isEqualTo(m.getYearFound());
        assertThat(returnedMeteorite.getWeight()).isEqualTo(m.getWeight());
        verify(meteoriteRepository, times(1)).findById("M398.1");
    }

    @Test
    void testFindByIDNotFound(){
        // Given
        given(meteoriteRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(()->{ //if an exception is thrown, thrown will catch it so it can be used
            Meteorite returnedMeteorite = meteoriteService.findByID("M398.1");
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find meteorite with Id M398.1" );
        verify(meteoriteRepository, times(1)).findById("M398.1");
    }

    @Test
    void testSaveMeteoriteSuccess(){
        //Given
        Meteorite newMeteorite = new Meteorite();
        newMeteorite.setMonnigNumber("M33.1");
        newMeteorite.setName("Nick's Meteor");
        newMeteorite.setCountry("");
        newMeteorite.setMClass("M33.1");
        newMeteorite.setMGroup("M33.1");
        newMeteorite.setMonnigNumber("M33.1");

        given(idWorker.nextId()).willReturn(123456L);
        given(meteoriteRepository.save(newMeteorite)).willReturn(newMeteorite);

        // When
        Meteorite savedMeteorite = meteoriteService.save(newMeteorite);

        // Then
        assertThat(savedMeteorite.getMonnigNumber()).isEqualTo("123456");
        assertThat(savedMeteorite.getName()).isEqualTo(newMeteorite.getName());
        assertThat(savedMeteorite.getCountry()).isEqualTo(newMeteorite.getCountry());
        assertThat(savedMeteorite.getMClass()).isEqualTo(newMeteorite.getMClass());
        assertThat(savedMeteorite.getMGroup()).isEqualTo(newMeteorite.getMGroup());
        verify(meteoriteRepository, times(1)).save(newMeteorite);

    }

    @Test
    void testDeleteMeteoriteSuccess(){
        // Given
        Meteorite meteorite = new Meteorite();
        meteorite.setMonnigNumber("M33.1");
        meteorite.setName("Nick's Meteor");
        meteorite.setCountry("");
        meteorite.setMClass("M33.1");
        meteorite.setMGroup("M33.1");
        meteorite.setMonnigNumber("M33.1");

        given(meteoriteRepository.findById("M33.1")).willReturn(Optional.of(meteorite));
        doNothing().when(meteoriteRepository).deleteById("M33.1");

        //When
        meteoriteService.delete("M33.1");

        //Then
        verify(meteoriteRepository, times(1)).deleteById("M33.1");


    }

    @Test
    void testDeleteMeteoriteFail(){
        //Given
        given(meteoriteRepository.findById("M33.1")).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            meteoriteService.delete("M33.1");
        });

        //Then
        verify(meteoriteRepository, times(1)).findById("M33.1");
    }

    @Test
    void testUpdateSuccess(){
        // Given
        Meteorite oldMeteorite = new Meteorite();

        oldMeteorite.setName("Abott");
        oldMeteorite.setMonnigNumber("M398.1");
        oldMeteorite.setCountry("USA");
        oldMeteorite.setMClass("Ordinary Chondrite");
        oldMeteorite.setMGroup("H");
        oldMeteorite.setYearFound("1951");
        oldMeteorite.setWeight("325.1");

        Meteorite update = new Meteorite();
        update.setName("Abott");
        update.setMonnigNumber("M398.1");
        update.setCountry("USA");
        update.setMClass("Ordinary Chondrite");
        update.setMGroup("H");
        update.setYearFound("2024");
        update.setWeight("325.1");

        given(meteoriteRepository.findById("M398.1")).willReturn(Optional.of(oldMeteorite));
        given(meteoriteRepository.save(oldMeteorite)).willReturn(oldMeteorite);
        // When
        Meteorite updatedMeteorite = meteoriteService.update("M398.1", update);

        // Then
        assertThat(updatedMeteorite.getMonnigNumber()).isEqualTo(update.getMonnigNumber());
        assertThat(updatedMeteorite.getYearFound()).isEqualTo(update.getYearFound());
        verify(meteoriteRepository, times(1)).findById("M398.1");
        verify(meteoriteRepository, times(1)).save(oldMeteorite);
    }

    @Test
    void testUpdateNotFound() {
        // Given
        Meteorite update = new Meteorite();
        update.setName("Abott");
        update.setMonnigNumber("M398.1");
        update.setCountry("USA");
        update.setMClass("Ordinary Chondrite");
        update.setMGroup("H");
        update.setYearFound("2024");
        update.setWeight("325.1");

        given(meteoriteRepository.findById("M398.1")).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, ()->{
            meteoriteService.update("M398.1", update);
        });

        // Then
        verify(meteoriteRepository, times(1)).findById("M398.1");
    }


    @Test
    void addSubsampleSuccess(){

        Meteorite oldMeteorite = new Meteorite();
        oldMeteorite.setName("Abott");
        oldMeteorite.setMonnigNumber("M398.1");
        oldMeteorite.setCountry("USA");
        oldMeteorite.setMClass("Ordinary Chondrite");
        oldMeteorite.setMGroup("H");
        oldMeteorite.setYearFound("1951");
        oldMeteorite.setWeight("325.1");

        Meteorite subsample = new Meteorite();
        subsample.setName("Abott");
        subsample.setMonnigNumber("M398.1.1");
        subsample.setCountry("USA");
        subsample.setMClass("Ordinary Chondrite");
        subsample.setMGroup("H");
        subsample.setYearFound("1951");
        subsample.setWeight("325.1");

        given(meteoriteRepository.save(subsample)).willReturn(subsample);
        given(meteoriteRepository.findById("M398.1.1")).willReturn(Optional.of(subsample));

        // When
        Meteorite subsampleMeteorite = meteoriteService.subsample("M398.1.1", oldMeteorite);

        // Then
        assertThat(subsampleMeteorite.getMonnigNumber()).isEqualTo(subsample.getMonnigNumber());
        assertThat(subsampleMeteorite.getYearFound()).isEqualTo(subsample.getYearFound());
        verify(meteoriteRepository, times(1)).findById("M398.1.1");
        verify(meteoriteRepository, times(1)).save(subsampleMeteorite);
    }


    @Test
    void testAddSubsampleNotFound() {

        Meteorite subsample = new Meteorite();
        subsample.setName("Abott");
        subsample.setMonnigNumber("M398.1.1");
        subsample.setCountry("USA");
        subsample.setMClass("Ordinary Chondrite");
        subsample.setMGroup("H");
        subsample.setYearFound("1951");
        subsample.setWeight("325.1");

        //Given
        given(meteoriteRepository.findById("M398.1.1")).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, ()->{
            meteoriteService.subsample("M398.1.1", subsample);
        });

        // Then
        verify(meteoriteRepository, times(1)).findById("M398.1.1");
    }
}