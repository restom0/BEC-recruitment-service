package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import vn.unigap.api.entity.Field;
import vn.unigap.api.entity.Province;
import vn.unigap.api.entity.Resume;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.repository.FieldRepository;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.repository.SeekerRepository;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeDtoOut {

    private Long id;
    private Long seekerId;
    private String seekerName;
    private String careerObj;
    private String title;
    private Double salary;
    private List<Long> fields;
    private List<Long> provinces;

    private static SeekerRepository seekerRepository;

    @Autowired
    public void setSeekerRepository(SeekerRepository seekerRepository) {
        ResumeDtoOut.seekerRepository = seekerRepository;
    }



    public static ResumeDtoOut from(Resume resume, String seekerName) {
        List<Long> fields = Arrays.stream(resume.getFields().split("-"))
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .collect(Collectors.toList());

        List<Long> provinces = Arrays.stream(resume.getProvinces().split("-"))
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .collect(Collectors.toList());


        return ResumeDtoOut.builder()
                .id(resume.getId())
                .seekerId(resume.getSeekerId())
                .seekerName(seekerName)
                .careerObj(resume.getCareerObj())
                .title(resume.getTitle())
                .salary(resume.getSalary().doubleValue())
                .fields(fields)
                .provinces(provinces)
                .build();
    }
}