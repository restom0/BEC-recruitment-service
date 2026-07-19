package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeDtoIn {

    @NotNull
    private Long seekerId;

    @NotEmpty
    private String careerObj;

    @NotEmpty
    private String title;

    @NotNull
    private Double salary;

    @NotNull
    private List<Integer> fieldIds;

    @NotNull
    private List<Integer> provinceIds;
}