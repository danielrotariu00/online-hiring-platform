package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.industry.IndustryResponse;
import com.licenta.databasemicroservice.persistence.entity.Industry;

public interface IIndustryService {
    IndustryResponse getIndustry(Integer industryId);

    Industry getIndustryOrElseThrowException(Integer industryId);

    Iterable<IndustryResponse> getIndustries();
}
