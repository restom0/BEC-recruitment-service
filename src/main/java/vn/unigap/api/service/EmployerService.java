package vn.unigap.api.service;

import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;

public interface EmployerService {
    EmployerDtoOut createEmployer(EmployerDtoIn employerDtoIn);
    EmployerDtoOut updateEmployer(Long id, EmployerDtoIn employerDtoIn);
    EmployerDtoOut getEmployer(Long id);
    void deleteEmployer(Long id);
    PageDtoOut<EmployerDtoOut> getAllEmployers(PageDtoIn pageDtoIn);
}
