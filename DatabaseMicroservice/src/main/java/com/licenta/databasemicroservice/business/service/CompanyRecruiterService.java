package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICompanyRecruiterService;
import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.model.CompanyRecruiterDTO;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CompanyRecruiterMapper;
import com.licenta.databasemicroservice.persistence.entity.Company;
import com.licenta.databasemicroservice.persistence.entity.CompanyRecruiter;
import com.licenta.databasemicroservice.persistence.repository.CompanyRecruiterRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyRecruiterService implements ICompanyRecruiterService {

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private CompanyRecruiterRepository companyRecruiterRepository;

    private final CompanyRecruiterMapper companyRecruiterMapper = Mappers.getMapper(CompanyRecruiterMapper.class);

    private static final String COMPANY_RECRUITER_NOT_FOUND_MESSAGE = "Recruiter with id <%d> does not exist for company with id <%d>.";
    private static final String COMPANY_RECRUITER_ALREADY_EXISTS_MESSAGE = "Recruiter with id <%d> already exists for company with id <%d>.";


    @Override
    public CompanyRecruiterDTO addCompanyRecruiter(@PathVariable Long companyId, @PathVariable Long recruiterId) {
        Company company = companyService.getCompanyOrElseThrowException(companyId);

        Optional<CompanyRecruiter> companyRecruiter =
                companyRecruiterRepository.findCompanyRecruiterByCompanyIdAndRecruiterId(companyId, recruiterId);

        if (companyRecruiter.isPresent()) {
            throw new AlreadyExistsException(String.format(COMPANY_RECRUITER_ALREADY_EXISTS_MESSAGE, recruiterId, companyId));
        }

        CompanyRecruiter newCompanyRecruiter = CompanyRecruiter.builder()
                .company(company)
                .recruiterId(recruiterId)
                .build();

        return companyRecruiterMapper.toDTO(companyRecruiterRepository.save(newCompanyRecruiter));
    }

    @Override
    public Iterable<CompanyRecruiterDTO> getCompanyRecruitersByCompany(Long companyId) {
        companyService.getCompanyOrElseThrowException(companyId);

        return companyRecruiterRepository.findCompanyRecruitersByCompanyId(companyId).stream()
                .map(companyRecruiterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompanyRecruiter(Long companyId, Long recruiterId) {
        companyService.getCompanyOrElseThrowException(companyId);

        Optional<CompanyRecruiter> companyRecruiter =
                companyRecruiterRepository.findCompanyRecruiterByCompanyIdAndRecruiterId(companyId, recruiterId);

        companyRecruiterRepository.delete(companyRecruiter.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_RECRUITER_NOT_FOUND_MESSAGE, recruiterId, companyId)))
        );
    }
}
