package com.licenta.database.business.models.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private String id;
    private String name;
    private String photo;
}
