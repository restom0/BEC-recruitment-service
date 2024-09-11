package vn.unigap.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    public Integer errorCode;
    public Integer statusCode;
    public String message;
    public Object object;
}
