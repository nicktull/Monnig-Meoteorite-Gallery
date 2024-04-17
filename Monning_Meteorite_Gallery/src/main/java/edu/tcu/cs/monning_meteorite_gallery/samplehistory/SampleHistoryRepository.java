package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleHistoryRepository extends JpaRepository<SampleHistory,String> {
}
