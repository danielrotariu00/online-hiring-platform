package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICompanyManagerService;
import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.model.CompanyManagerDTO;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CompanyManagerMapper;
import com.licenta.databasemicroservice.persistence.entity.Company;
import com.licenta.databasemicroservice.persistence.entity.CompanyManager;
import com.licenta.databasemicroservice.persistence.repository.CompanyManagerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyManagerService implements ICompanyManagerService {

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private CompanyManagerRepository companyManagerRepository;

    private final CompanyManagerMapper companyManagerMapper = Mappers.getMapper(CompanyManagerMapper.class);

    private static final String COMPANY_MANAGER_NOT_FOUND_MESSAGE = "Manager with id <%d> does not exist for company with id <%d>.";
    private static final String COMPANY_MANAGER_ALREADY_EXISTS_MESSAGE = "Manager with id <%d> already exists for company with id <%d>.";


    @Override
    public CompanyManagerDTO addCompanyManager(Long companyId, Long managerId) {
        Company company = companyService.getCompanyOrElseThrowException(companyId);

        Optional<CompanyManager> companyManager =
                companyManagerRepository.findCompanyManagerByCompanyIdAndManagerId(companyId, managerId);

        if (companyManager.isPresent()) {
            throw new AlreadyExistsException(String.format(COMPANY_MANAGER_ALREADY_EXISTS_MESSAGE, managerId, companyId));
        }

        CompanyManager newCompanyManager = CompanyManager.builder()
                .company(company)
                .managerId(managerId)
                .build();

        return companyManagerMapper.toDTO(companyManagerRepository.save(newCompanyManager));
    }

    @Override
    public Iterable<CompanyManagerDTO> getCompanyManagersByCompany(Long companyId) {
        companyService.getCompanyOrElseThrowException(companyId);

        return companyManagerRepository.findCompanyManagersByCompanyId(companyId).stream()
                .map(companyManagerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompanyManager(Long companyId, Long managerId) {
        companyService.getCompanyOrElseThrowException(companyId);

        Optional<CompanyManager> companyManager =
                companyManagerRepository.findCompanyManagerByCompanyIdAndManagerId(companyId, managerId);

        companyManagerRepository.delete(companyManager.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_MANAGER_NOT_FOUND_MESSAGE, managerId, companyId)))
        );
    }
}
