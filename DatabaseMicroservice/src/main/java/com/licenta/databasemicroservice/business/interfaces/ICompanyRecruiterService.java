package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CompanyRecruiterDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface ICompanyRecruiterService {

    CompanyRecruiterDTO addCompanyRecruiter(@PathVariable Long companyId, @PathVariable Long recruiterId);

    void deleteCompanyRecruiter(Long companyId, Long recruiterId);

    Iterable<CompanyRecruiterDTO> getCompanyRecruitersByCompany(Long companyId);
}
