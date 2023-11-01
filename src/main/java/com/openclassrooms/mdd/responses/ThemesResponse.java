package com.openclassrooms.mdd.responses;

import java.util.List;

import com.openclassrooms.mdd.model.Theme;

public class ThemesResponse {
    private List<Theme> themes;

    public ThemesResponse(List<Theme> themes) {
        this.themes = themes;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }
}
