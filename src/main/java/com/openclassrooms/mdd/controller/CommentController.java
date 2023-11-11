package com.openclassrooms.mdd.controller;

import com.openclassrooms.dto.ArticleRequest;
import com.openclassrooms.dto.CommentRequest;
import com.openclassrooms.dto.LoginRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.mdd.exception.NotFoundException;
import com.openclassrooms.mdd.model.Article;
import com.openclassrooms.mdd.model.Comment;
import com.openclassrooms.mdd.model.Theme;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.ArticleRepository;
import com.openclassrooms.mdd.repository.CommentRepository;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.responses.ArticleResponse;
import com.openclassrooms.mdd.responses.ArticlesResponse;
import com.openclassrooms.mdd.responses.AuthSuccess;
import com.openclassrooms.mdd.responses.CommentResponse;
import com.openclassrooms.mdd.responses.CommentsResponse;
import com.openclassrooms.mdd.responses.ThemesResponse;
import com.openclassrooms.mdd.security.JwtTokenProvider;
import com.openclassrooms.mdd.service.ArticleService;
import com.openclassrooms.mdd.service.CommentService;
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
@RequestMapping(path = "/api/comment")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;
    

  @GetMapping("/all")
  @Operation(summary = "Show the list of all comments")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public ResponseEntity<CommentsResponse> getAllComments() {

    // Récupère tous les commentaires
    Iterable<Comment> commentsIterable = commentService.findAll();

    // Convertit l'itérable en liste
    List<Comment> commentsList = new ArrayList<>();
    commentsIterable.forEach(commentsList::add);

    // Crée l'objet CommentsResponse contenant les commentaires
    CommentsResponse commentsResponse = new CommentsResponse(commentsList);

    // Retourne l'objet CommentsResponse dans la réponse
    return ResponseEntity.ok(commentsResponse);

  }

  @GetMapping("/list/{articleId}")
  @Operation(summary = "Show comments for a specific article")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public ResponseEntity<CommentsResponse> getCommentsByArticle(@PathVariable Integer articleId) {
    
    List<Comment> comments = commentService.getCommentsByArticle(articleId);
    CommentsResponse commentsResponse = new CommentsResponse(comments);
    return ResponseEntity.ok(commentsResponse);
    
  }

  @PostMapping(path = "/add")
  @Operation(summary = "Create a new comment")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Comment created !"),
      @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public ResponseEntity<CommentResponse> createPost(@RequestBody CommentRequest commentRequest) {
  
          try {
              commentService.createComment(commentRequest);

              CommentResponse commentResponse = new CommentResponse("Comment created!");

              return ResponseEntity.ok(commentResponse);
          } catch (IOException e) {
              e.printStackTrace();
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
          }
  }


}


