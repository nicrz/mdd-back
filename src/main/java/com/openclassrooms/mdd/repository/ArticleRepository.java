package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository  extends JpaRepository<Article, Integer> {

    @Query("SELECT DISTINCT a FROM Article a " +
    "JOIN Theme t ON a.theme_id = t.id " +
    "JOIN Subscription s ON t.id = s.theme_id " +
    "JOIN User u ON s.user_id = u.id " +
    "WHERE u.id = :userId")
List<Article> findArticlesByUserThemes(@Param("userId") Integer userId);
}