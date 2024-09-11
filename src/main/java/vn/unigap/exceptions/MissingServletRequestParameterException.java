package vn.unigap.exceptions;

public class MissingServletRequestParameterException extends RuntimeException{
    public MissingServletRequestParameterException(String message){
        super(message);
    }
}
