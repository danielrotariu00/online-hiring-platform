package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.ICompanyFollowerService;
import com.licenta.database.business.models.companyfollower.CompanyFollowerRequest;
import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.user.UserResponse;
import com.licenta.database.business.util.converters.CompanyConverter;
import com.licenta.database.business.util.converters.UserConverter;
import com.licenta.database.business.util.exceptions.AlreadyExistsException;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.validators.Validator;
import com.licenta.database.persistence.models.Company;
import com.licenta.database.persistence.models.CompanyFollower;
import com.licenta.database.persistence.models.User;
import com.licenta.database.persistence.repositories.CompanyFollowerRepository;
import com.licenta.database.persistence.repositories.CompanyRepository;
import com.licenta.database.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.licenta.database.business.services.UserService.USER_NOT_FOUND_MESSAGE;

@Service
public class CompanyFollowerService implements ICompanyFollowerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyFollowerRepository companyFollowerRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private CompanyConverter companyConverter;

    @Autowired
    private Validator validator;

    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with id <%s> does not exist.";
    private static final String COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE = "User with id <%s> already follows company with id <%s>.";
    private static final String COMPANY_FOLLOWER_NOT_FOUND_MESSAGE = "User with id <%s> does not follow company with id <%s>.";

    @Override
    public void addCompanyFollower(CompanyFollowerRequest request) {
        validator.validate(request);

        String userId = request.getUserId();
        String companyId = request.getCompanyId();

        User user = getUserOrElseThrowException(userId);
        Company company = getCompanyOrElseThrowException(companyId);

        Optional<CompanyFollower> follower = companyFollowerRepository.findByCompany_IdAndUser_Id(companyId, userId);

        if (follower.isPresent()) {
            throw new AlreadyExistsException(String.format(COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE, userId, companyId));
        }

        CompanyFollower newFollower = CompanyFollower.builder()
                .user(user)
                .company(company)
                .build();

        companyFollowerRepository.save(newFollower);
    }

    @Override
    public Iterable<UserResponse> getCompanyFollowers(String companyId) {
        validator.validate(companyId);

        getCompanyOrElseThrowException(companyId);

        return companyFollowerRepository.findCompanyFollowersByCompany_Id(companyId).stream()
                .map(companyFollower -> userRepository.findById(companyFollower.getUser().getId()))
                .map(user -> userConverter.toResponse(user.get()))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CompanyResponse> getCompaniesFollowedByUser(String userId) {
        validator.validate(userId);

        getUserOrElseThrowException(userId);

        return companyFollowerRepository.findCompanyFollowersByUser_Id(userId).stream()
                .map(companyFollower -> companyRepository.findById(companyFollower.getCompany().getId()))
                .map(company -> companyConverter.toResponse(company.get()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeCompanyFollower(CompanyFollowerRequest request) {
        validator.validate(request);

        String userId = request.getUserId();
        String companyId = request.getCompanyId();

        getUserOrElseThrowException(userId);
        getCompanyOrElseThrowException(companyId);

        Optional<CompanyFollower> follower =
                companyFollowerRepository.findByCompany_IdAndUser_Id(companyId, userId);

        companyFollowerRepository.delete(follower.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_FOLLOWER_NOT_FOUND_MESSAGE, userId, companyId)))
        );
    }

    private User getUserOrElseThrowException(String id) {

        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id))
        );
    }

    private Company getCompanyOrElseThrowException(String id) {

        return companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id))
        );
    }
}
