package com.openclassrooms.mdd.controller;

import com.openclassrooms.dto.LoginRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.mdd.model.Theme;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.responses.AuthSuccess;
import com.openclassrooms.mdd.responses.ThemesResponse;
import com.openclassrooms.mdd.security.JwtTokenProvider;
import com.openclassrooms.mdd.service.ThemeService;
import com.openclassrooms.mdd.service.UserService;

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
@RequestMapping(path = "/api/theme")
@SecurityRequirement(name = "bearerAuth")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Autowired
    private UserRepository userRepository;
    

  @GetMapping("/list")
  @Operation(summary = "Show the list of all thematics")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Theme.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
    public ResponseEntity<ThemesResponse> getAllThemes() {

    // Récupère tous les thèmes
    Iterable<Theme> themesIterable = themeService.findAll();

    // Convertit l'itérable en liste
    List<Theme> themesList = new ArrayList<>();
    themesIterable.forEach(themesList::add);

    // Crée l'objet ThemesResponse contenant les thèmes
    ThemesResponse themesResponse = new ThemesResponse(themesList);

    // Retourne l'objet ThemesResponse dans la réponse
    return ResponseEntity.ok(themesResponse);

  }

  @PostMapping("{id}/subscribe/{userId}")
  public ResponseEntity<?> subscribe(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
      try {
          this.themeService.subscribe(id, userId);

          return ResponseEntity.ok().build();
      } catch (NumberFormatException e) {
          return ResponseEntity.badRequest().build();
      }
  }

  @DeleteMapping("{id}/subscribe/{userId}")
  public ResponseEntity<?> noLongerSubscribe(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
      try {
          this.themeService.noLongerSubscribe(id, userId);

          return ResponseEntity.ok().build();
      } catch (NumberFormatException e) {
          return ResponseEntity.badRequest().build();
      }
  }


}