package com.openclassrooms.mdd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.openclassrooms.mdd.model.Article;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer id;

  @Size(max = 1000)
  @Column(nullable = false)
  @Getter @Setter private String content;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @Getter @Setter private User user_id;

  @ManyToOne
  @JoinColumn(name = "article_id", referencedColumnName = "id")
  @Getter @Setter private Article article;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  @Getter @Setter private Timestamp created_at;

}

