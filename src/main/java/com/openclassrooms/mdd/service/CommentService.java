package com.openclassrooms.mdd.service;

import com.openclassrooms.dto.ArticleRequest;
import com.openclassrooms.dto.CommentRequest;
import com.openclassrooms.mdd.exception.NotFoundException;
import com.openclassrooms.mdd.model.Article;
import com.openclassrooms.mdd.model.Comment;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.model.Theme;
import com.openclassrooms.mdd.repository.ArticleRepository;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.repository.CommentRepository;

import io.jsonwebtoken.io.IOException;

import com.openclassrooms.mdd.repository.ThemeRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    public Optional<Comment> getById(Integer id) {
        return this.commentRepository.findById(id);
    }

    public List<Comment> getCommentsByArticle(Integer articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article not found"));
    
        return commentRepository.findByArticle(article);
    }

    public Comment createComment(CommentRequest commentRequest) throws IOException {

        Comment newComment = new Comment();
        newComment.setContent(commentRequest.getContent());
        Timestamp now = Timestamp.from(Instant.now());
        newComment.setCreated_at(now);
    
        // Récupére l'article à partir de son ID
        Article article = articleRepository.findById(commentRequest.getArticle_id())
                .orElseThrow(() -> new NotFoundException("Post not found"));
    
        newComment.setArticle(article);
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Récupére l'ID de l'utilisateur authentifié
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        newComment.setUser_id(user);
    
        commentRepository.save(newComment);
    
        return newComment;
    }

}
