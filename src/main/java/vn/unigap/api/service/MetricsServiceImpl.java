// MetricsService.java
package vn.unigap.api.service;

import org.springframework.stereotype.Service;
import vn.unigap.api.repository.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetricsServiceImpl implements MetricsService{
    private final EmployerRepository employerRepository;
    private final JobRepository jobRepository;
    private final SeekerRepository seekerRepository;
    private final ResumeRepository resumeRepository;

    public MetricsServiceImpl(EmployerRepository employerRepository, JobRepository jobRepository, SeekerRepository seekerRepository, ResumeRepository resumeRepository) {
        this.employerRepository = employerRepository;
        this.jobRepository = jobRepository;
        this.seekerRepository = seekerRepository;
        this.resumeRepository = resumeRepository;
    }

    public int getNumEmployers(LocalDate fromDate, LocalDate toDate) {
        return employerRepository.countByCreatedAtBetween(fromDate, toDate);
    }

    public int getNumJobs(LocalDate fromDate, LocalDate toDate) {
        return jobRepository.countByCreatedAtBetween(fromDate, toDate);
    }

    public int getNumSeekers(LocalDate fromDate, LocalDate toDate) {
        return seekerRepository.countByCreatedAtBetween(fromDate, toDate);
    }

    public int getNumResumes(LocalDate fromDate, LocalDate toDate) {
        return resumeRepository.countByCreatedAtBetween(fromDate, toDate);
    }

    public List<Map<String, Object>> getDailyMetrics(LocalDate fromDate, LocalDate toDate) {
        List<Map<String, Object>> dailyMetrics = new ArrayList<>();

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("date", date);

            LocalDate startOfDay = date;
            LocalDate endOfDay = date.plusDays(1);

            metrics.put("numEmployer", employerRepository.countByCreatedAtBetween(startOfDay, endOfDay));
            metrics.put("numJob", jobRepository.countByCreatedAtBetween(startOfDay, endOfDay));
            metrics.put("numSeeker", seekerRepository.countByCreatedAtBetween(startOfDay, endOfDay));
            metrics.put("numResume", resumeRepository.countByCreatedAtBetween(startOfDay, endOfDay));

            dailyMetrics.add(metrics);
        }

        return dailyMetrics;
    }

}