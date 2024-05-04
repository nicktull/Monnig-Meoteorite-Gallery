package edu.tcu.cs.monning_meteorite_gallery.meteorite;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteoriteRepository extends JpaRepository<Meteorite, String> {

}
