package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.Province;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.util.HashMap;

@Service
@CacheConfig(cacheNames={"employers"})
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public EmployerServiceImpl(EmployerRepository employerRepository, ProvinceRepository provinceRepository) {
        this.employerRepository = employerRepository;
        this.provinceRepository = provinceRepository;
    }


    @CacheEvict(key = "#result.id")
    @Override
    public EmployerDtoOut createEmployer(EmployerDtoIn employerDtoIn) {

        employerRepository.findByEmail(employerDtoIn.getEmail())
                .ifPresent(employer -> {
                    throw new ApiException(ErrorCode.BAD_REQUEST,
                            HttpStatus.BAD_REQUEST, "email already existed");
                });

         Province province = provinceRepository.findById(employerDtoIn.getProvinceId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST, "Invalid provinceId"));

        Employer employer = employerRepository.save(Employer.builder()
                .email(employerDtoIn.getEmail())
                .name(employerDtoIn.getName())
                .province(employerDtoIn.getProvinceId())
                .description(employerDtoIn.getDescription())
                .build());

        return EmployerDtoOut.from(employer,province.getName());
    }

    @CacheEvict(key = "#id")
    @Override
    public EmployerDtoOut updateEmployer(Long id, EmployerDtoIn employerDtoIn) {

        Employer employer = employerRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found")
        );

        Province province = provinceRepository.findById(employerDtoIn.getProvinceId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST, "Invalid provinceId"));

        employer.setName(employerDtoIn.getName());
        employer.setEmail(employerDtoIn.getEmail());
        employer.setDescription(employerDtoIn.getDescription());
        employer.setProvince(employerDtoIn.getProvinceId());

        employer = employerRepository.save(employer);

        return EmployerDtoOut.from(employer,province.getName());
    }

    @Override
    @Cacheable(key = "#id")
    public EmployerDtoOut getEmployer(Long id) {
        Employer employer = employerRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found")
        );
        Province province = provinceRepository.findById(employer.getProvince())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST, "Invalid provinceId"));
        return EmployerDtoOut.from(employer, province.getName());
    }

    @CacheEvict(key = "#id")
    @Override
    public void deleteEmployer(Long id) {
        Employer employer = employerRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found")
        );

        employerRepository.delete(employer);
    }

    @Cacheable(key = "#pageDtoIn.page + '-' + #pageDtoIn.pageSize")
    @Override
    public PageDtoOut<EmployerDtoOut> getAllEmployers(PageDtoIn pageDtoIn) {

        Page<Employer> employerPage = this.employerRepository.findAll(PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(),
                Sort.by("name").ascending()));
        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), employerPage.getTotalElements(),
                employerPage.stream().map((Employer employer) -> {
                    Province province = provinceRepository.findById(employer.getProvince())
                            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST, "Invalid provinceId"));
                   return EmployerDtoOut.from(employer, province.getName());
                }).toList());
    }
}
