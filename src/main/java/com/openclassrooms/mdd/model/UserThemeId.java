package com.openclassrooms.mdd.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserThemeId implements Serializable {

  private User user_id;
  private Theme theme_id;
}