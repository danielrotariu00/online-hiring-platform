package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CompanyManagerDTO;

public interface ICompanyManagerService {

    CompanyManagerDTO addCompanyManager(Long companyId, Long managerId);

    void deleteCompanyManager(Long companyId, Long managerId);

    Iterable<CompanyManagerDTO> getCompanyManagersByCompany(Long companyId);
}
