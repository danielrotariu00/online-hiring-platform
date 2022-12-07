package com.licenta.databasemicroservice.business.model.industry;

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
public class IndustryResponse {
    private Integer id;
    private String name;
}
