package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import com.licenta.databasemicroservice.persistence.repository.CompanyIndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyIndustryService {

    @Autowired
    private CompanyIndustryRepository companyIndustryRepository;

    static final String COMPANY_INDUSTRY_NOT_FOUND_MESSAGE = "Company Industry with id <%s> does not exist.";

    public CompanyIndustry getCompanyIndustryOrElseThrowException(Integer companyIndustryId) {

        return companyIndustryRepository.findById(companyIndustryId).orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_INDUSTRY_NOT_FOUND_MESSAGE, companyIndustryId))
        );
    }
}
