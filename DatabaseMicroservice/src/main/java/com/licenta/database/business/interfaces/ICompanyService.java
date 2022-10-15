package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.company.CreateCompanyRequest;

public interface ICompanyService {

    void createCompany(CreateCompanyRequest request);
    CompanyResponse getCompany(String id);
    Iterable<CompanyResponse> getCompanies();
    void deleteCompany(String id);
}
