package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICompanyIndustryService;
import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.interfaces.IIndustryService;
import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryRequest;
import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CompanyIndustryMapper;
import com.licenta.databasemicroservice.persistence.entity.Company;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import com.licenta.databasemicroservice.persistence.entity.Industry;
import com.licenta.databasemicroservice.persistence.repository.CompanyIndustryRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyIndustryService implements ICompanyIndustryService {

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IIndustryService industryService;
    @Autowired
    private CompanyIndustryRepository companyIndustryRepository;

    private final CompanyIndustryMapper companyIndustryMapper = Mappers.getMapper(CompanyIndustryMapper.class);

    private static final String COMPANY_INDUSTRY_NOT_FOUND_BY_ID_MESSAGE = "CompanyIndustry with id <%d> does not exist.";
    private static final String COMPANY_INDUSTRY_NOT_FOUND_MESSAGE = "Industry with id <%d> does not exist for company with id <%d>.";
    private static final String COMPANY_INDUSTRY_ALREADY_EXISTS_MESSAGE = "Industry with id <%d> already exists for company with id <%d>.";


    @Override
    public CompanyIndustryResponse addCompanyIndustry(CompanyIndustryRequest request) {
        Long companyId = request.getCompanyId();
        Integer industryId = request.getIndustryId();

        Company company = companyService.getCompanyOrElseThrowException(companyId);
        Industry industry = industryService.getIndustryOrElseThrowException(industryId);

        Optional<CompanyIndustry> companyIndustry =
                companyIndustryRepository.findCompanyIndustryByCompanyIdAndIndustryId(companyId, industryId);

        if (companyIndustry.isPresent()) {
            throw new AlreadyExistsException(String.format(COMPANY_INDUSTRY_ALREADY_EXISTS_MESSAGE, industryId, companyId));
        }

        CompanyIndustry newCompanyIndustry = CompanyIndustry.builder()
                .company(company)
                .industry(industry)
                .build();

        return companyIndustryMapper.toResponse(companyIndustryRepository.save(newCompanyIndustry));
    }

    @Override
    public Iterable<CompanyIndustryResponse> getCompanyIndustriesByCompany(Long companyId) {
        companyService.getCompanyOrElseThrowException(companyId);

        return companyIndustryRepository.findCompanyIndustriesByCompanyId(companyId).stream()
                .map(companyIndustryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CompanyResponse> getCompaniesByIndustry(Integer industryId) {
        industryService.getIndustryOrElseThrowException(industryId);

        return companyIndustryRepository.findCompanyIndustriesByIndustryId(industryId).stream()
                .map(companyIndustry -> companyService.getCompany(companyIndustry.getCompany().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompanyIndustry(Long companyId, Integer industryId) {
        companyService.getCompanyOrElseThrowException(companyId);
        industryService.getIndustryOrElseThrowException(industryId);

        Optional<CompanyIndustry> companyIndustry =
                companyIndustryRepository.findCompanyIndustryByCompanyIdAndIndustryId(companyId, industryId);

        companyIndustryRepository.delete(companyIndustry.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_INDUSTRY_NOT_FOUND_MESSAGE, industryId, companyId)))
        );
    }

    @Override
    public CompanyIndustry getCompanyIndustryOrElseThrowException(Long companyIndustryId) {

        return companyIndustryRepository.findById(companyIndustryId).orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_INDUSTRY_NOT_FOUND_BY_ID_MESSAGE, companyIndustryId))
        );
    }
}
