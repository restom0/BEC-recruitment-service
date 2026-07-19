package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.unigap.api.entity.Job;

import java.time.LocalDate;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByEmployerId(Long employerId, Pageable pageable);

    @Query("SELECT COUNT(j) FROM Job j WHERE FUNCTION('DATE', j.createdAt) BETWEEN :fromDate AND :toDate")
    int countByCreatedAtBetween(LocalDate fromDate, LocalDate toDate);

    Object countByCreatedAt(LocalDate date);
}
