package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeekerDtoIn {

    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    private LocalDate birthday;

    private String address;

    @NotNull
    private Integer provinceId;
}