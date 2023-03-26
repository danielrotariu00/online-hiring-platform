package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.company.CompanyDTO;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryDTO;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;

public interface ICompanyIndustryService {

    CompanyIndustry getCompanyIndustryOrElseThrowException(Long companyIndustryId);

    Iterable<CompanyDTO> getCompaniesByIndustry(Integer industryId);

    CompanyIndustryDTO addCompanyIndustry(CompanyIndustryDTO request);

    void deleteCompanyIndustry(Long companyId, Integer industryId);

    Iterable<CompanyIndustryDTO> getCompanyIndustriesByCompany(Long companyId);
}
