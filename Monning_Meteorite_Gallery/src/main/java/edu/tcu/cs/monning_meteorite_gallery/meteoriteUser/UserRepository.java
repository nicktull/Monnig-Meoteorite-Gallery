package edu.tcu.cs.monning_meteorite_gallery.meteoriteUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MeteoriteUser, Integer> {

    Optional<MeteoriteUser> findByUsername(String username);

}