package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.company.CompanyDTO;
import com.licenta.databasemicroservice.persistence.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICompanyService {

    void createCompany(CompanyDTO request);
    CompanyDTO updateCompany(Long companyId, CompanyDTO request);
    CompanyDTO getCompany(Long companyId);
    Iterable<CompanyDTO> getCompanies();
    void deleteCompany(Long companyId);

    Company getCompanyOrElseThrowException(Long id);

    void saveImage(Long companyId, MultipartFile image) throws IOException;
}
