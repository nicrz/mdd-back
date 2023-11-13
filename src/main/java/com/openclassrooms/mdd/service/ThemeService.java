package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.exception.NotFoundException;
import com.openclassrooms.mdd.exception.UnauthorizedException;
import com.openclassrooms.mdd.model.Theme;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.ThemeRepository;
import com.openclassrooms.mdd.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public void subscribe(Integer id, Integer userId) {
        Theme theme = this.themeRepository.findById(id).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);
        if (theme == null || user == null) {
            throw new NotFoundException(null);
        }

        boolean alreadySubscribe = theme.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if(alreadySubscribe) {
            throw new UnauthorizedException(null);
        }

        theme.getUsers().add(user);

        this.themeRepository.save(theme);
    }

    public void noLongerSubscribe(Integer id, Integer userId) {
        Theme theme = this.themeRepository.findById(id).orElse(null);
        if (theme == null) {
            throw new NotFoundException(null);
        }

        boolean alreadySubscribe = theme.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if(!alreadySubscribe) {
            throw new UnauthorizedException(null);
        }

        theme.setUsers(theme.getUsers().stream().filter(user -> !user.getId().equals(userId)).collect(Collectors.toList()));

        this.themeRepository.save(theme);
    }

}