package vn.unigap.api.service;

import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;

public interface ResumeService {
    ResumeDtoOut createResume(ResumeDtoIn resumeDtoIn);
    ResumeDtoOut updateResume(Long id, ResumeDtoIn resumeDtoIn);
    PageDtoOut<ResumeDtoOut> getAllResumes(int page, int pageSize, Long employerId);
    ResumeDtoOut getResume(Long id);
    void deleteResume(Long id);
}
