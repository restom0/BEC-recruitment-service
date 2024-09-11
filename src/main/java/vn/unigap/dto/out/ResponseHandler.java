package vn.unigap.dto.out;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(Integer errorCode, Integer statusCode, String message, Object object, HttpStatus httpStatus){
        Map<String,Object> response =new HashMap<>();
        response.put("errorCode",errorCode);
        response.put("statusCode",statusCode);
        response.put("message",message);
        response.put("object",object);
        return new ResponseEntity<>(response,httpStatus);
    }
}


