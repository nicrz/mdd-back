package com.openclassrooms.mdd.service;

import com.openclassrooms.dto.ArticleRequest;
import com.openclassrooms.mdd.exception.NotFoundException;
import com.openclassrooms.mdd.model.Article;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.model.Theme;
import com.openclassrooms.mdd.repository.ArticleRepository;
import com.openclassrooms.mdd.repository.UserRepository;

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
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final ThemeRepository themeRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
    }

    public Article save(Article article) {
        return this.articleRepository.save(article);
    }

    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> getById(Integer id) {
        return this.articleRepository.findById(id);
    }

    public Article createArticle(ArticleRequest articleRequest) throws IOException {

        Article newArticle = new Article();
        newArticle.setTitle(articleRequest.getTitle());
        newArticle.setContent(articleRequest.getContent());
        Timestamp now = Timestamp.from(Instant.now());
        newArticle.setCreated_at(now);
        newArticle.setUpdated_at(now);
    
        // Récupére le thème à partir de son ID
        Theme theme = themeRepository.findById(articleRequest.getTheme_id())
                .orElseThrow(() -> new NotFoundException("Theme not found"));
    
        newArticle.setTheme_id(theme);
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Récupére l'ID de l'utilisateur authentifié
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        newArticle.setAuthor_id(user);
    
        // Enregistre l'objet Article dans la base de données
        articleRepository.save(newArticle);
    
        return newArticle;
    }

}
