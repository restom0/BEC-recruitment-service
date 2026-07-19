package vn.unigap.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;
import vn.unigap.api.entity.Field;
import vn.unigap.api.entity.Province;
import vn.unigap.api.entity.Resume;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.repository.FieldRepository;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.repository.ResumeRepository;
import vn.unigap.api.repository.SeekerRepository;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final SeekerRepository seekerRepository;
    private final FieldRepository fieldRepository;
    private final ProvinceRepository provinceRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository, SeekerRepository seekerRepository, FieldRepository fieldRepository, ProvinceRepository provinceRepository) {
        this.resumeRepository = resumeRepository;
        this.seekerRepository = seekerRepository;
        this.fieldRepository = fieldRepository;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public ResumeDtoOut createResume(ResumeDtoIn resumeDtoIn) {
        Seeker seeker = seekerRepository.findById(resumeDtoIn.getSeekerId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found"));

        String fields = resumeDtoIn.getFieldIds().stream()
                .map(id -> fieldRepository.findById(id)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Field not found")))
                .map(Field::getId)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "-", "-"));

        String provinces = resumeDtoIn.getProvinceIds().stream()
                .map(id -> provinceRepository.findById(Math.toIntExact(Long.valueOf(id)))
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province not found")))
                .map(Province::getId)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "-", "-"));

        Resume resume = Resume.builder()
                .seekerId(resumeDtoIn.getSeekerId())
                .careerObj(resumeDtoIn.getCareerObj())
                .title(resumeDtoIn.getTitle())
                .salary(resumeDtoIn.getSalary().intValue())
                .fields(fields)
                .provinces(provinces)
                .build();

        Resume savedResume = resumeRepository.save(resume);

        return ResumeDtoOut.from(savedResume, seeker.getName());
    }

    @Override
    public ResumeDtoOut updateResume(Long id, ResumeDtoIn resumeDtoIn) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Resume not found"));

        if (resumeDtoIn.getSeekerId() == null) {
            throw new IllegalArgumentException("The given seekerId must not be null");
        }

        Seeker seeker = seekerRepository.findById(resumeDtoIn.getSeekerId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found"));

        String fields = resumeDtoIn.getFieldIds().stream()
                .filter(fieldId -> fieldId != null)
                .map(fieldId -> fieldRepository.findById(fieldId)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Field not found")))
                .map(Field::getId)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "-", "-"));

        String provinces = resumeDtoIn.getProvinceIds().stream()
                .filter(provinceId -> provinceId != null)
                .map(provinceId -> provinceRepository.findById(Math.toIntExact(Long.valueOf(provinceId)))
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province not found")))
                .map(Province::getId)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "-", "-"));

        resume.setSeekerId(resumeDtoIn.getSeekerId());
        resume.setCareerObj(resumeDtoIn.getCareerObj());
        resume.setTitle(resumeDtoIn.getTitle());
        resume.setSalary(resumeDtoIn.getSalary().intValue());
        resume.setFields(fields);
        resume.setProvinces(provinces);

        Resume updatedResume = resumeRepository.save(resume);

        return ResumeDtoOut.from(updatedResume, seeker.getName());
    }

    @Override
    public PageDtoOut<ResumeDtoOut> getAllResumes(int page, int pageSize, Long seekerId) {
        // Create Pageable with sorting by expiredAt descending and employer.name ascending
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("title"));

        // Get jobs from the database based on employerId
        Page<Resume> resumePage;
        if (seekerId == -1) {
            resumePage = (Page<Resume>) resumeRepository.findAllOrderBySeekerName(pageable);
        } else {
            resumePage = (Page<Resume>) resumeRepository.findBySeekerIdOrderBySeekerName(seekerId, pageable);
        }

        List<ResumeDtoOut> resumeDtoOuts = resumePage.stream()
                .map(resume -> {
                    Optional<Seeker> seeker = seekerRepository.findById(resume.getSeekerId());
                    if (seeker.isEmpty() && seekerId != -1) {
                        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found");
                    }

                    String seekerName = seeker.isEmpty() ? "" : seeker.get().getName();

                    return ResumeDtoOut.from(resume, seekerName);
                })
                .collect(Collectors.toList());


        return PageDtoOut.from(page, pageSize, resumePage.getTotalElements(),
                resumeDtoOuts);
    }


    @Override
    public ResumeDtoOut getResume(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Resume not found"));

        Seeker seeker = seekerRepository.findById(resume.getSeekerId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Seeker not found"));

        return ResumeDtoOut.from(resume, seeker.getName());
    }

    @Override
    public void deleteResume(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Resume not found"));

        resumeRepository.delete(resume);
    }
}
