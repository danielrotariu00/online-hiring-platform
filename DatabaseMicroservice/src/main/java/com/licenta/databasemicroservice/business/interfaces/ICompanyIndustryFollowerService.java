package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.CompanyIndustryDTO;
import com.licenta.databasemicroservice.business.model.UserDTO;

public interface ICompanyIndustryFollowerService {

    void addCompanyIndustryFollower(Long userId, Long companyIndustryId);
    Iterable<UserDTO> getCompanyIndustryFollowers(Long companyIndustryId);
    Iterable<CompanyIndustryDTO> getFollowedCompanyIndustries(Long userId);
    void removeCompanyIndustryFollower(Long userId, Long companyIndustryId);
    void removeFollowedCompanyIndustries(Long userId);
}