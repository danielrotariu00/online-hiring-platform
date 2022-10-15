package com.licenta.database.business.interfaces;

import com.licenta.database.business.models.companyfollower.CompanyFollowerRequest;
import com.licenta.database.business.models.company.CompanyResponse;
import com.licenta.database.business.models.user.UserResponse;

public interface ICompanyFollowerService {

    void addCompanyFollower(CompanyFollowerRequest request);
    Iterable<UserResponse> getCompanyFollowers(String companyId);
    Iterable<CompanyResponse> getCompaniesFollowedByUser(String userId);
    void removeCompanyFollower(CompanyFollowerRequest request);
}