package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.company.CreateCompanyRequest;
import com.licenta.databasemicroservice.persistence.entity.Company;

public interface ICompanyService {

    void createCompany(CreateCompanyRequest request);
    CompanyResponse getCompany(Long companyId);
    Iterable<CompanyResponse> getCompanies();
    void deleteCompany(Long companyId);

    Company getCompanyOrElseThrowException(Long id);
}
