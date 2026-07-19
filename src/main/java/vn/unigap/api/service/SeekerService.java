package vn.unigap.api.service;

import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;

import java.util.List;

public interface SeekerService {
    SeekerDtoOut createSeeker(SeekerDtoIn seekerDtoIn);
    SeekerDtoOut updateSeeker(Long id,  SeekerDtoIn seekerDtoIn);
    SeekerDtoOut getSeeker(Long id);
    void deleteSeeker(Long id);
    PageDtoOut<SeekerDtoOut> getAllSeekers(int page, int pageSize, Long provinceId);
}