package com.lite.blackdream.sdk;

/**
 * @author LaineyC
 */
public class AppException extends RuntimeException {

	private ErrorMessage errorMessage;

    public AppException(Throwable throwable, ErrorMessage errorMessage){
        super(throwable);
        this.errorMessage = errorMessage;
    }

    public AppException(Throwable throwable, String errorMessage){
        super(throwable);
        this.errorMessage = new ErrorMessage(errorMessage);
    }

    public AppException(ErrorMessage errorMessage){
        this.errorMessage = errorMessage;
    }

    public AppException(String errorMessage){
        this.errorMessage = new ErrorMessage(errorMessage);
    }

    public boolean hasErrorMessage(){
        return errorMessage != null;
    }

    public ErrorMessage getErrorMessage(){
        return errorMessage;
    }

}
