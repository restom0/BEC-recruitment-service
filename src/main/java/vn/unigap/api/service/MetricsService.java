package vn.unigap.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MetricsService {
    int getNumEmployers(LocalDate fromDate, LocalDate toDate);
    int getNumJobs(LocalDate fromDate, LocalDate toDate);
    int getNumSeekers(LocalDate fromDate, LocalDate toDate);
    int getNumResumes(LocalDate fromDate, LocalDate toDate);
    List<Map<String, Object>> getDailyMetrics(LocalDate fromDate, LocalDate toDate);
}