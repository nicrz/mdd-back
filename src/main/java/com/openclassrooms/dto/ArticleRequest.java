package com.openclassrooms.dto;

public class ArticleRequest {
    private String title;
    private String content;
    private Integer theme_id;

    public ArticleRequest(String title, String content, Integer theme_id) {
        this.title = title;
        this.content = content;
        this.theme_id = theme_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Integer theme_id) {
        this.theme_id = theme_id;
    }

}
