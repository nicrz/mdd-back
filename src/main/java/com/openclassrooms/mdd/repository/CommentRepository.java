package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Comment;
import com.openclassrooms.mdd.model.Article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticle(Article article);
}