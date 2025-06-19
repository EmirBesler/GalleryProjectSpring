package com.codeSlayer.galleryProject.Response;

public class GetResponseWrapper<T>{
    private String message;
    private T response;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getResponse() {
        return response;
    }
    public void setResponse(T response) {
        this.response = response;
    }
}
