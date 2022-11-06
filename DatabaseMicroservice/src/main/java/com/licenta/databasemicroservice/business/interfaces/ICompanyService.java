package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.company.CreateCompanyRequest;

public interface ICompanyService {

    void createCompany(CreateCompanyRequest request);
    CompanyResponse getCompany(String companyId);
    Iterable<CompanyResponse> getCompanies();
    void deleteCompany(String companyId);
}
