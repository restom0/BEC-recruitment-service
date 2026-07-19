package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDtoIn {

    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotNull
    private Long employerId;

    @NotNull
    private Integer quantity;

    @NotEmpty
    @Size(max = 1000)
    private String description;

    @NotNull
    private List<Integer> fieldIds;

    @NotNull
    private List<Integer> provinceIds;

    @NotNull
    private Double salary;

    @NotNull
    private LocalDate expiredAt;
}