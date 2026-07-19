package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.unigap.api.entity.Employer;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer,Long> {
    Optional<Employer> findByEmail(String email);

    @Query("SELECT COUNT(e) FROM Employer e WHERE FUNCTION('DATE', e.createdAt) BETWEEN :fromDate AND :toDate")
    int countByCreatedAtBetween(LocalDate fromDate, LocalDate toDate);

}
