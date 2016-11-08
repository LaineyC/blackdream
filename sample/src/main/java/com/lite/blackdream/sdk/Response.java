package com.lite.blackdream.sdk;

/**
 * @author LaineyC
 */
public class Response<T> {

    private ErrorMessage error;

    private T body;

    public Response(){

    }

    public Response(T body){
        setBody(body);
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }

    public boolean hasError(){
        return this.error != null;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
