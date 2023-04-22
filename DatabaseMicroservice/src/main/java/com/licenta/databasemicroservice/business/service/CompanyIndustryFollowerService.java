package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.ICompanyIndustryFollowerService;
import com.licenta.databasemicroservice.business.interfaces.ICompanyIndustryService;
import com.licenta.databasemicroservice.business.model.CompanyIndustryDTO;
import com.licenta.databasemicroservice.business.model.UserDTO;
import com.licenta.databasemicroservice.business.util.exception.AlreadyExistsException;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.CompanyIndustryMapper;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import com.licenta.databasemicroservice.persistence.entity.CompanyIndustryFollower;
import com.licenta.databasemicroservice.persistence.repository.CompanyIndustryFollowerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompanyIndustryFollowerService implements ICompanyIndustryFollowerService {

    @Autowired
    private ICompanyIndustryService companyIndustryService;
    @Autowired
    private CompanyIndustryFollowerRepository companyIndustryFollowerRepository;

    private final CompanyIndustryMapper companyIndustryMapper = Mappers.getMapper(CompanyIndustryMapper.class);

    private static final String COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE = "User with id <%s> already follows company industry with id <%s>.";
    private static final String COMPANY_FOLLOWER_NOT_FOUND_MESSAGE = "User with id <%s> does not follow company industry with id <%s>.";

    @Override
    public void addCompanyIndustryFollower(Long userId, Long companyIndustryId) {
        CompanyIndustry companyIndustry = companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        Optional<CompanyIndustryFollower> follower =
                companyIndustryFollowerRepository.findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(companyIndustryId, userId);

        if (follower.isPresent()) {
            throw new AlreadyExistsException(String.format(COMPANY_FOLLOWER_ALREADY_EXISTS_MESSAGE, userId, companyIndustryId));
        }

        CompanyIndustryFollower newCompanyIndustryFollower = CompanyIndustryFollower.builder()
                .userId(userId)
                .companyIndustry(companyIndustry)
                .build();

        companyIndustryFollowerRepository.save(newCompanyIndustryFollower);
    }

    @Override
    public Iterable<UserDTO> getCompanyIndustryFollowers(Long companyIndustryId) {

        companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        return companyIndustryFollowerRepository.findCompanyIndustryFollowersByCompanyIndustryId(companyIndustryId).stream()
                .map(follower -> UserDTO.builder()
                        .id(follower.getUserId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<CompanyIndustryDTO> getFollowedCompanyIndustries(Long userId) {

        return companyIndustryFollowerRepository.findCompanyIndustryFollowersByUserId(userId).stream()
                .map(follower -> companyIndustryService.getCompanyIndustryOrElseThrowException(follower.getCompanyIndustry().getId()))
                .map(companyIndustryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeCompanyIndustryFollower(Long userId, Long companyIndustryId) {
        companyIndustryService.getCompanyIndustryOrElseThrowException(companyIndustryId);

        Optional<CompanyIndustryFollower> follower =
                companyIndustryFollowerRepository.findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(companyIndustryId, userId);

        companyIndustryFollowerRepository.delete(follower.orElseThrow(
                () -> new NotFoundException(String.format(COMPANY_FOLLOWER_NOT_FOUND_MESSAGE, userId, companyIndustryId)))
        );
    }

    @Override
    public void removeFollowedCompanyIndustries(Long userId) {
        companyIndustryFollowerRepository.deleteAllByUserId(userId);
    }
}
