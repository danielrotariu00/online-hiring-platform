package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CompanyRecruiterDTO;

public interface ICompanyRecruiterService {

    CompanyRecruiterDTO addCompanyRecruiter(CompanyRecruiterDTO request);

    void deleteCompanyRecruiter(Long companyId, Long recruiterId);

    Iterable<CompanyRecruiterDTO> getCompanyRecruitersByCompany(Long companyId);
}
