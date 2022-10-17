package com.licenta.database.business.services;

import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.persistence.entities.CompanyIndustry;
import com.licenta.database.persistence.repositories.CompanyIndustryRepository;
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
