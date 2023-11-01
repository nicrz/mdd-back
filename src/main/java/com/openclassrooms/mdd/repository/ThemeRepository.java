package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository  extends JpaRepository<Theme, Integer> {
}
