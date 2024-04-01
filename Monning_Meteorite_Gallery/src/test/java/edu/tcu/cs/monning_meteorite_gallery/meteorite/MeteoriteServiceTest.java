package edu.tcu.cs.monning_meteorite_gallery.meteorite;

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

    }

    @Test
    void testAddMeteoriteFail(){

    }

    @Test
    void testDeleteMeteoriteSuccess(){

    }

    @Test
    void testDeleteMeteoriteFail(){

    }
}