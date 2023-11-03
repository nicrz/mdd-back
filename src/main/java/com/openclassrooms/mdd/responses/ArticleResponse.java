package com.openclassrooms.mdd.responses;

public class ArticleResponse {
    private String message;

    public ArticleResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
