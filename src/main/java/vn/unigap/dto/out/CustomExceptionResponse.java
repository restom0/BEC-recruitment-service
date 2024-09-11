package vn.unigap.dto.out;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.unigap.exceptions.BadRequestException;
import vn.unigap.exceptions.ConflictException;
import vn.unigap.exceptions.InternalServerErrorException;
import vn.unigap.exceptions.NotFoundException;

@RestControllerAdvice
public class CustomExceptionResponse {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException ex, WebRequest req){
        return new ExceptionResponse(404, null, ex.getMessage(), null);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(BadRequestException ex, WebRequest req){
        return new ExceptionResponse(400, null, ex.getMessage(), null);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex){
        return new ExceptionResponse(400, null, ex.getParameterName()+" is missing", null);
    }
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleConflictException(ConflictException ex, WebRequest req){
        return new ExceptionResponse(409, null, ex.getMessage(), null);
    }
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerErrorException(InternalServerErrorException ex, WebRequest req){
        return new ExceptionResponse(500, null, ex.getMessage(), null);
    }
}
