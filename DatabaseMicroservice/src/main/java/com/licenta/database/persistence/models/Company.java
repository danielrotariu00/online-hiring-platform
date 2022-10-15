package com.licenta.database.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="company")
public class Company {

    @Id
    @NotEmpty
    private String id;

    @NotEmpty
    @Column(unique=true)
    private String name;

    @NotEmpty
    private String photo;

    @OneToMany(mappedBy = "company")
    private Set<CompanyFollower> followers;
}
