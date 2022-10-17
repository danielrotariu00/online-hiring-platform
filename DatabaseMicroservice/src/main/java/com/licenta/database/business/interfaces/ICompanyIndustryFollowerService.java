package com.licenta.database.business.interfaces;

import com.licenta.database.business.model.companyindustry.CompanyIndustryResponse;
import com.licenta.database.business.model.companyindustryfollower.CompanyIndustryFollowerRequest;
import com.licenta.database.business.model.user.UserResponse;

public interface ICompanyIndustryFollowerService {

    void addCompanyIndustryFollower(CompanyIndustryFollowerRequest request);
    Iterable<UserResponse> getCompanyIndustryFollowers(Integer companyIndustryId);
    Iterable<CompanyIndustryResponse> getFollowedCompanyIndustries(String userId);
    void removeCompanyIndustryFollower(CompanyIndustryFollowerRequest request);
}