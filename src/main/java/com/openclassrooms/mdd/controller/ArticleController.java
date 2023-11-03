package com.openclassrooms.mdd.controller;

import com.openclassrooms.dto.ArticleRequest;
import com.openclassrooms.dto.LoginRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.mdd.exception.NotFoundException;
import com.openclassrooms.mdd.model.Article;
import com.openclassrooms.mdd.model.Theme;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.responses.ArticleResponse;
import com.openclassrooms.mdd.responses.ArticlesResponse;
import com.openclassrooms.mdd.responses.AuthSuccess;
import com.openclassrooms.mdd.responses.ThemesResponse;
import com.openclassrooms.mdd.security.JwtTokenProvider;
import com.openclassrooms.mdd.service.ArticleService;
import com.openclassrooms.mdd.service.ThemeService;
import com.openclassrooms.mdd.service.UserService;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/article")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    private UserRepository userRepository;
    

  @GetMapping("/list")
  @Operation(summary = "Show the list of all posts")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Article.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public ResponseEntity<ArticlesResponse> getAllPosts() {

    // Récupère tous les articles
    Iterable<Article> articlesIterable = articleService.findAll();

    // Convertit l'itérable en liste
    List<Article> articlesList = new ArrayList<>();
    articlesIterable.forEach(articlesList::add);

    // Crée l'objet ThemesResponse contenant les thèmes
    ArticlesResponse articlesResponse = new ArticlesResponse(articlesList);

    // Retourne l'objet ThemesResponse dans la réponse
    return ResponseEntity.ok(articlesResponse);

  }

  @GetMapping(path = "/detail/{id}")
  @Operation(summary = "Show a post by ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Article.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public @ResponseBody Optional<Article> getPost(@PathVariable Integer id) {
    
    Optional<Article> article = articleService.getById(id);

    if (article.isPresent()) {
        return article;
    } else {
        throw new NotFoundException("Post not found");
    }
  }

  @PostMapping(path = "/add")
  @Operation(summary = "Create a new post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Post created !"),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public ResponseEntity<ArticleResponse> createPost(@RequestBody ArticleRequest articleRequest) {
  
          try {
              articleService.createArticle(articleRequest);

              ArticleResponse articleResponse = new ArticleResponse("Post created!");

              return ResponseEntity.ok(articleResponse);
          } catch (IOException e) {
              e.printStackTrace();
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
          }
  }


}


