package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.Employer;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoOut implements Serializable {


    private Long id;
    private String email;
    private String name;
    private int provinceId;
    private String provinceName;
    private String description;

    public static EmployerDtoOut from(Employer employer, String provinceName) {


        return EmployerDtoOut.builder()
                .id(employer.getId())
                .email(employer.getEmail())
                .name(employer.getName())
                .provinceId(employer.getProvince())
                .provinceName(provinceName)
                .description(employer.getDescription())
                .build();
    }
}
