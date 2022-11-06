package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.databasemicroservice.business.model.companyindustryfollower.CompanyIndustryFollowerRequest;
import com.licenta.databasemicroservice.business.model.user.UserResponse;

public interface ICompanyIndustryFollowerService {

    void addCompanyIndustryFollower(CompanyIndustryFollowerRequest request);
    Iterable<UserResponse> getCompanyIndustryFollowers(Integer companyIndustryId);
    Iterable<CompanyIndustryResponse> getFollowedCompanyIndustries(String userId);
    void removeCompanyIndustryFollower(CompanyIndustryFollowerRequest request);
}