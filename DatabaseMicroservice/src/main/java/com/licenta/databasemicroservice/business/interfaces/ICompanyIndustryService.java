package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CompanyDTO;
import com.licenta.databasemicroservice.business.model.CompanyIndustryDTO;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;

public interface ICompanyIndustryService {

    CompanyIndustry getCompanyIndustryOrElseThrowException(Long companyIndustryId);

    Iterable<CompanyDTO> getCompaniesByIndustry(Integer industryId);

    CompanyIndustryDTO addCompanyIndustry(Long companyId, Integer industryId);

    void deleteCompanyIndustry(Long companyId, Integer industryId);

    Iterable<CompanyIndustryDTO> getCompanyIndustriesByCompany(Long companyId);
}
