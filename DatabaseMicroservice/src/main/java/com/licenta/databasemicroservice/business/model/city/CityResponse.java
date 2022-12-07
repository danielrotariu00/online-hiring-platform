package com.licenta.databasemicroservice.business.model.city;

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
public class CityResponse {

    private Long id;
    private String name;
    private Integer countryId;
}
