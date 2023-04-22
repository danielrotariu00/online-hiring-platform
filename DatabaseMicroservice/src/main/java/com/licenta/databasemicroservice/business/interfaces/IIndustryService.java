package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.IndustryDTO;
import com.licenta.databasemicroservice.persistence.entity.Industry;

public interface IIndustryService {
    IndustryDTO getIndustry(Integer industryId);

    Industry getIndustryOrElseThrowException(Integer industryId);

    Iterable<IndustryDTO> getIndustries();
}
