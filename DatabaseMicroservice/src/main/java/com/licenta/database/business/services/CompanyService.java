package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.ICompanyService;
import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.company.CreateCompanyRequest;
import com.licenta.database.business.util.converters.CompanyConverter;
import com.licenta.database.business.util.exceptions.AlreadyExistsException;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.validators.Validator;
import com.licenta.database.persistence.models.Company;
import com.licenta.database.persistence.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyConverter companyConverter;
    @Autowired
    private Validator validator;

    private static final String NAME_ALREADY_IN_USE_MESSAGE = "Name <%s> is already in use.";
    protected static final String COMPANY_NOT_FOUND_MESSAGE = "Company with id <%s> does not exist.";

    private Company getCompanyOrElseThrowException(String id) {

        return companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id))
        );
    }

    @Override
    public void createCompany(CreateCompanyRequest request) {
        validator.validate(request);

        String name = request.getName();

        if (companyRepository.findCompanyByName(name).isPresent()) {
            throw new AlreadyExistsException(String.format(NAME_ALREADY_IN_USE_MESSAGE, name));
        }

        Company company = companyConverter.toModel(request);
        companyRepository.save(company);
    }

    @Override
    public CompanyResponse getCompany(String id) {
        validator.validate(id);

        Company company = getCompanyOrElseThrowException(id);

        return companyConverter.toResponse(company);
    }

    @Override
    public Iterable<CompanyResponse> getCompanies() {
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(company -> companyConverter.toResponse(company))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompany(String id) {
        validator.validate(id);

        getCompanyOrElseThrowException(id);

        companyRepository.deleteById(id);
    }
}
