package com.licenta.database.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="company")
public class Company {

    @Id
    @NonNull
    private String id = UUID.randomUUID().toString();

    @NonNull
    @Column(unique=true)
    private String name;

    @NonNull
    private String photo;

    @OneToMany(mappedBy = "company")
    private Set<CompanyFollower> followers;
}
