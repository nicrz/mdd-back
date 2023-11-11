package com.openclassrooms.mdd.responses;

public class CommentResponse {
    private String message;

    public CommentResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}