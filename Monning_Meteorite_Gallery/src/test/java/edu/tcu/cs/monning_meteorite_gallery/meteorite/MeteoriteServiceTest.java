package edu.tcu.cs.monning_meteorite_gallery.meteorite;

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
        assertThat(thrown).isInstanceOf(MeteoriteNotFoundException.class).hasMessage("Could not find meteorite with monnig number M398.1" );
        verify(meteoriteRepository, times(1)).findById("M398.1");
    }

    @Test
    void testAddMeteoriteSuccess(){
        //Given
        Meteorite newMeteorite = new Meteorite();
        newMeteorite.setMonnigNumber("M33.1");
        newMeteorite.setName("Nick's Meteor");
        newMeteorite.setCountry("");
        newMeteorite.setMClass("M33.1");
        newMeteorite.setMGroup("M33.1");
        newMeteorite.setMonnigNumber("M33.1");

    }

//    @Test
//    void testSaveSuccess(){
//        //Given
//        Artifact newArtifact = new Artifact();
//        newArtifact.setName("Artifact 3");
//        newArtifact.setDescription("Description");
//        newArtifact.setImageURL("ImageUrl...");
//
//        given(idWorker.nextId()).willReturn(123456L);
//        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);
//
//        //When
//        Artifact savedArtifact = artifactService.save(newArtifact);
//
//        //Then
//        assertThat(savedArtifact.getId()).isEqualTo("123456");
//        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
//        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
//        assertThat(savedArtifact.getImageURL()).isEqualTo(newArtifact.getImageURL());
//        verify(artifactRepository, times(1)).save(newArtifact);
//    }

    @Test
    void testDeleteMeteoriteSuccess(){

    }

    @Test
    void testDeleteMeteoriteFail(){

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

    //Test fail due to save not implemented
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
        assertThrows(MethodArgumentNotValidException.class, ()->{
            meteoriteService.update("M398.1", update);
        });

        // Then
        verify(meteoriteRepository, times(1)).findById("M398.1");
    }
}