package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryRequest;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;

public interface ICompanyIndustryService {

    CompanyIndustry getCompanyIndustryOrElseThrowException(Long companyIndustryId);

    Iterable<CompanyResponse> getCompaniesByIndustry(Integer industryId);

    void addCompanyIndustry(CompanyIndustryRequest request);

    void deleteCompanyIndustry(CompanyIndustryRequest request);

    Iterable<CompanyIndustryResponse> getCompanyIndustriesByCompany(Long companyId);
}
