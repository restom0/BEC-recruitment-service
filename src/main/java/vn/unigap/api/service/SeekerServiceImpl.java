package vn.unigap.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;
import vn.unigap.api.entity.Province;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.repository.SeekerRepository;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.time.LocalDate;

@Service
public class SeekerServiceImpl implements SeekerService {

    private final SeekerRepository seekerRepository;
    private final ProvinceRepository provinceRepository;

    public SeekerServiceImpl(SeekerRepository seekerRepository, ProvinceRepository provinceRepository) {
        this.seekerRepository = seekerRepository;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public SeekerDtoOut createSeeker(SeekerDtoIn seekerDtoIn) {
        Province province = provinceRepository.findById(seekerDtoIn.getProvinceId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province not found"));

        Seeker savedSeeker = seekerRepository.save(
                Seeker.builder()
                        .name(seekerDtoIn.getName())
                        .birthday(seekerDtoIn.getBirthday())
                        .address(seekerDtoIn.getAddress())
                        .province(province)
                        .build()
        );

        return SeekerDtoOut.from(savedSeeker);
    }

    @Override
    public SeekerDtoOut updateSeeker(Long id, SeekerDtoIn seekerDtoIn) {
        Seeker seeker = seekerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found"));

        Province province = provinceRepository.findById(seekerDtoIn.getProvinceId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Invalid provinceId"));

        seeker.setName(seekerDtoIn.getName());
        seeker.setBirthday(seekerDtoIn.getBirthday());
        seeker.setAddress(seekerDtoIn.getAddress());
        seeker.setProvince(province); // Set the Province entity, not the id

        Seeker updatedSeeker = seekerRepository.save(seeker);

        return SeekerDtoOut.from(updatedSeeker);
    }

    @Override
    public SeekerDtoOut getSeeker(Long id) {
        Seeker seeker = seekerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found"));

        return SeekerDtoOut.from(seeker);
    }




    @Override
    public void deleteSeeker(Long id) {
        Seeker seeker = seekerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found"));

        seekerRepository.delete(seeker);
    }


    @Override
    public PageDtoOut<SeekerDtoOut> getAllSeekers(int page, int pageSize, Long provinceId) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("name"));
        Page<Seeker> seekerPage;

        if (provinceId == -1) {
            seekerPage = seekerRepository.findAll(pageable);
        } else {
            seekerPage = seekerRepository.findByProvinceId(provinceId, pageable);
        }

        return PageDtoOut.from(page, pageSize, seekerPage.getTotalElements(),
                seekerPage.stream().map(SeekerDtoOut::from).toList());
    }
}