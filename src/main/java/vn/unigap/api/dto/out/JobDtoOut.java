package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.Job;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDtoOut {

    private Long id;
    private String title;
    private int quantity;
    private String description;
    private Double salary;
    private List<Integer> fieldIds;
    private List<Integer> provinceIds;
    private LocalDate expiredAt;

    public static JobDtoOut from(Job job) {
        List<Integer> fieldIds = Stream.of(job.getFields().split("-"))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> provinceIds = Stream.of(job.getProvinces().split("-"))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        JobDtoOut result = JobDtoOut.builder()
                .id(job.getId())
                .title(job.getTitle())
                .quantity(job.getQuantity())
                .description(job.getDescription())
                .salary(job.getSalary())
                .fieldIds(fieldIds)
                .provinceIds(provinceIds)
                .expiredAt(job.getExpiredAt())
                .build();
        return result;
    }
}