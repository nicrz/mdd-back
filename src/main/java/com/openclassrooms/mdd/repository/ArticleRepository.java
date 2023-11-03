package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository  extends JpaRepository<Article, Integer> {
}