package vn.unigap.common.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.unigap.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        if (e.getHttpStatus().is4xxClientError()) {
            logger.debug("Client error: ", e);
        } else if (e.getHttpStatus().is5xxServerError()) {
            logger.error("Server error: ", e);
        }
        return responseEntity(e.getErrorCode(), e.getHttpStatus(), e.getMessage());
    }

    private ResponseEntity<Object> responseEntity(Integer errorCode, HttpStatusCode statusCode, String msg) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .errorCode(errorCode)
                        .statusCode(statusCode.value())
                        .message(msg)
                        .build(),
                statusCode);
    }

}
