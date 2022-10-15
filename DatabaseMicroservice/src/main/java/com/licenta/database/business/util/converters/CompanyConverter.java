package com.licenta.database.business.util.converters;

import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.company.CreateCompanyRequest;
import com.licenta.database.persistence.models.Company;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter {

    private final ModelMapper mapper = new ModelMapper();

    public Company toModel(@NonNull CreateCompanyRequest request) {

        return mapper.map(request, Company.class);
    }

    public CompanyResponse toResponse(@NonNull Company company) {

        return mapper.map(company, CompanyResponse.class);
    }
}
