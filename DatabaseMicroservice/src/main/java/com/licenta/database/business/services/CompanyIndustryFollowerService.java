package com.licenta.database.business.services;

import com.licenta.database.business.interfaces.ICompanyIndustryFollowerService;
import com.licenta.database.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.database.business.model.companyindustryfollower.CompanyIndustryFollowerRequest;
import com.licenta.database.business.model.user.UserResponse;
import com.licenta.database.business.util.exceptions.AlreadyExistsException;
import com.licenta.database.business.util.exceptions.NotFoundException;
import com.licenta.database.business.util.mappers.CompanyIndustryMapper;
import com.licenta.database.persistence.entities.CompanyIndustry;
import com.licenta.database.persistence.entities.CompanyIndustryFollower;
import com.licenta.database.persistence.entities.User;
import com.licenta.database.persistence.repositories.CompanyIndustryFollowerRepository;
import com.licenta.database.persistence.repositories.CompanyIndustryRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompanyIndustryFollowerService implements ICompanyIndustryFollowerService {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyIndustryService companyIndustryService;
    @Autowired
    private CompanyIndustryRepository  companyIndustryRepository;
    @Autowired
    private CompanyIndustryFollowerRepository companyIndustryFollowerRepository;

    private final CompanyIndustryMapper companyIndustryMapper = Mappers.getMapper(CompanyIndustryMapper.class);

    private static final String COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE = "User with id <%s> already follows company industry with id <%s>.";
    private static final String COMPANY_FOLLOWER_NOT_FOUND_MESSAGE = "User with id <%s> does not follow company industry with id <%s>.";

    @Override
    public void addCompanyIndustryFollower(CompanyIndustryFollowerRequest request) {
        String userId = request.getUserId();
        Integer companyIndustryId = request.getCompanyIndustryId();

        User user = userService.getUserOrElseThrowException(userId);
        CompanyIndustry companyIndustry = companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        Optional<CompanyIndustryFollower> follower =
                companyIndustryFollowerRepository.findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(companyIndustryId, userId);

        if (follower.isPresent()) {
            throw new AlreadyExistsException(String.format(COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE, userId, companyIndustryId));
        }

        CompanyIndustryFollower newCompanyIndustryFollower = CompanyIndustryFollower.builder()
                .user(user)
                .companyIndustry(companyIndustry)
                .build();

        companyIndustryFollowerRepository.save(newCompanyIndustryFollower);
    }

    @Override
    public Iterable<UserResponse> getCompanyIndustryFollowers(Integer companyIndustryId) {

        companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        return companyIndustryFollowerRepository.findCompanyIndustryFollowersByCompanyIndustryId(companyIndustryId).stream()
                .map(follower -> userService.getUser(follower.getUser().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CompanyIndustryResponse> getFollowedCompanyIndustries(String userId) {

        userService.getUserOrElseThrowException(userId);

        return companyIndustryFollowerRepository.findCompanyIndustryFollowersByUserId(userId).stream()
                .map(follower -> companyIndustryRepository.findById(follower.getCompanyIndustry().getId()))
                .map(companyIndustry -> companyIndustryMapper.toResponse(companyIndustry.get()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeCompanyIndustryFollower(CompanyIndustryFollowerRequest request) {
        String userId = request.getUserId();
        Integer companyIndustryId = request.getCompanyIndustryId();

        userService.getUserOrElseThrowException(userId);
        companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        Optional<CompanyIndustryFollower> follower =
                companyIndustryFollowerRepository.findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(companyIndustryId, userId);

        companyIndustryFollowerRepository.delete(follower.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_FOLLOWER_NOT_FOUND_MESSAGE, userId, companyIndustryId)))
        );
    }
}
