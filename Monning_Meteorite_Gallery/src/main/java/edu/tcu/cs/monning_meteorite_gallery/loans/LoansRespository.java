package edu.tcu.cs.monning_meteorite_gallery.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRespository extends JpaRepository<Loans, Integer> {
}
