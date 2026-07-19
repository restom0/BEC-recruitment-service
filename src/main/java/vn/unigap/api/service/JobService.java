package vn.unigap.api.service;

import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;

public interface JobService {
    JobDtoOut createJob(JobDtoIn jobDtoIn);
    JobDtoOut updateJob(Long id, JobDtoIn jobDtoIn);
    JobDtoOut getJob(Long id);
    void deleteJob(Long id);
    PageDtoOut<JobDtoOut> getAllJobs(int page, int pageSize, Long employerId);
}
