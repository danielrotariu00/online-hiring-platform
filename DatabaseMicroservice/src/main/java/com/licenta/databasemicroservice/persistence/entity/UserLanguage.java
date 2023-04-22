package com.licenta.databasemicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_language")
public class UserLanguage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable=false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable=false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "language_level_id", nullable=false)
    private LanguageLevel languageLevel;
}
