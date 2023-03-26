package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryDTO;
import com.licenta.databasemicroservice.business.model.companyindustryfollower.CompanyIndustryFollowerRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;

public interface ICompanyIndustryFollowerService {

    void addCompanyIndustryFollower(CompanyIndustryFollowerRequest request);
    Iterable<UserResponse> getCompanyIndustryFollowers(Long companyIndustryId);
    Iterable<CompanyIndustryDTO> getFollowedCompanyIndustries(Long userId);
    void removeCompanyIndustryFollower(Long userId, Long companyIndustryId);
}