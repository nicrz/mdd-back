package com.openclassrooms.mdd.responses;

import java.util.List;

import com.openclassrooms.mdd.model.Article;

public class ArticlesResponse {
    private List<Article> articles;

    public ArticlesResponse(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

