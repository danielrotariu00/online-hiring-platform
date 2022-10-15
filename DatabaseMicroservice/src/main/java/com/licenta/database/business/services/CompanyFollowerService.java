package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.ICompanyFollowerService;
import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.companyfollower.CompanyFollowerRequest;
import com.licenta.database.business.models.user.UserResponse;
import com.licenta.database.business.util.exceptions.AlreadyExistsException;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.mappers.CompanyMapper;
import com.licenta.database.persistence.models.Company;
import com.licenta.database.persistence.models.CompanyFollower;
import com.licenta.database.persistence.models.User;
import com.licenta.database.persistence.repositories.CompanyFollowerRepository;
import com.licenta.database.persistence.repositories.CompanyRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompanyFollowerService implements ICompanyFollowerService {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyFollowerRepository companyFollowerRepository;

    private final CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);

    private static final String COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE = "User with id <%s> already follows company with id <%s>.";
    private static final String COMPANY_FOLLOWER_NOT_FOUND_MESSAGE = "User with id <%s> does not follow company with id <%s>.";

    @Override
    public void addCompanyFollower(CompanyFollowerRequest request) {
        String userId = request.getUserId();
        String companyId = request.getCompanyId();

        User user = userService.getUserOrElseThrowException(userId);
        Company company = companyService.getCompanyOrElseThrowException(companyId);

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

        companyService.getCompanyOrElseThrowException(companyId);

        return companyFollowerRepository.findCompanyFollowersByCompany_Id(companyId).stream()
                .map(companyFollower -> userService.getUser(companyFollower.getUser().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CompanyResponse> getCompaniesFollowedByUser(String userId) {

        userService.getUserOrElseThrowException(userId);

        return companyFollowerRepository.findCompanyFollowersByUser_Id(userId).stream()
                .map(companyFollower -> companyRepository.findById(companyFollower.getCompany().getId()))
                .map(company -> companyMapper.toResponse(company.get()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeCompanyFollower(CompanyFollowerRequest request) {
        String userId = request.getUserId();
        String companyId = request.getCompanyId();

        userService.getUserOrElseThrowException(userId);
        companyService.getCompanyOrElseThrowException(companyId);

        Optional<CompanyFollower> follower =
                companyFollowerRepository.findByCompany_IdAndUser_Id(companyId, userId);

        companyFollowerRepository.delete(follower.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_FOLLOWER_NOT_FOUND_MESSAGE, userId, companyId)))
        );
    }
}
