package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICompanyService;
import com.licenta.databasemicroservice.business.model.company.CompanyResponse;
import com.licenta.databasemicroservice.business.model.company.CreateCompanyRequest;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CompanyMapper;
import com.licenta.databasemicroservice.persistence.entity.Company;
import com.licenta.databasemicroservice.persistence.repository.CompanyRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    private final CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);

    private static final String NAME_ALREADY_IN_USE_MESSAGE = "Name <%s> is already in use.";
    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with id <%s> does not exist.";

    @Override
    public void createCompany(CreateCompanyRequest request) {
        String name = request.getName();

        if (companyRepository.findCompanyByName(name).isPresent()) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_IN_USE_MESSAGE, name));
        }

        Company company = companyMapper.toModel(request);
        companyRepository.save(company);
    }

    @Override
    public CompanyResponse getCompany(Long id) {
        Company company = getCompanyOrElseThrowException(id);

        return companyMapper.toResponse(company);
    }

    @Override
    public Iterable<CompanyResponse> getCompanies() {

        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompany(Long id) {

        getCompanyOrElseThrowException(id);

        companyRepository.deleteById(id);
    }

    @Override
    public Company getCompanyOrElseThrowException(Long id) {

        return companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id))
        );
    }
}
