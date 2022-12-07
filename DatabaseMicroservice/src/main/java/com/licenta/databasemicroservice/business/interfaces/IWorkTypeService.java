package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.worktype.WorkTypeResponse;
import com.licenta.databasemicroservice.persistence.entity.WorkType;

public interface IWorkTypeService {

    WorkType getWorkTypeOrElseThrowException(Integer workTypeId);

    Iterable<WorkTypeResponse> getWorkTypes();

    WorkTypeResponse getWorkType(Integer workTypeId);
}
