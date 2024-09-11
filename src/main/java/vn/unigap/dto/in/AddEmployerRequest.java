package vn.unigap.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddEmployerRequest {
    public String email;
    public String name;
    public Integer provinceId;
    public String description;
}
