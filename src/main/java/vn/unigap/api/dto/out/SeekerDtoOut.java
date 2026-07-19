package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.Seeker;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeekerDtoOut {

    private Long id;

    private String name;

    private LocalDate birthday;

    private String address;

    private Integer provinceId;

    private String provinceName;

    public static SeekerDtoOut from(Seeker seeker) {


        SeekerDtoOut dto = new SeekerDtoOut();
        dto.setId(seeker.getId());
        dto.setName(seeker.getName());
        dto.setBirthday(seeker.getBirthday());
        dto.setAddress(seeker.getAddress());
        // Check if Province is null before accessing its methods
        if (seeker.getProvince() != null) {
            dto.setProvinceId(seeker.getProvince().getId());
            dto.setProvinceName(seeker.getProvince().getName());
        }
        return dto;
    }
}