package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.unigap.api.entity.Seeker;

import java.time.LocalDate;

public interface SeekerRepository extends JpaRepository<Seeker, Long> {
    @Query("SELECT s FROM Seeker s WHERE s.province.id = :provinceId")
    Page<Seeker> findByProvinceId(Long provinceId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Seeker s WHERE FUNCTION('DATE', s.createdAt) BETWEEN :fromDate AND :toDate")
    int countByCreatedAtBetween(LocalDate fromDate, LocalDate toDate);

    Object countByCreatedAt(LocalDate date);
}