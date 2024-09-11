package vn.unigap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.unigap.entities.Employer;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer,Long> {
    Optional<Employer> findByEmail(String email);
    @Query(value = "SELECT * FROM employer LIMIT :limit OFFSET :offset",nativeQuery = true)
    Iterable<Employer> findAllOffsetLimit(Integer offset, Integer limit);
    Long deleteByEmail(String email);
}

