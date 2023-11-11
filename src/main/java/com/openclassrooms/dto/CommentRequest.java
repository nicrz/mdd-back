package com.openclassrooms.dto;

public class CommentRequest {
    private String content;
    private Integer article_id;

    public CommentRequest(String content, Integer article_id) {
        this.content = content;
        this.article_id = article_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

}

