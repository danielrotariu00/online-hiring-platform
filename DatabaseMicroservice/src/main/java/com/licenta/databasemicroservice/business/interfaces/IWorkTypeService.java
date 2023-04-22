package com.licenta.databasemicroservice.business.interfaces;

import com.licenta.databasemicroservice.business.model.WorkTypeDTO;
import com.licenta.databasemicroservice.persistence.entity.WorkType;

public interface IWorkTypeService {

    WorkType getWorkTypeOrElseThrowException(Integer workTypeId);

    Iterable<WorkTypeDTO> getWorkTypes();

    WorkTypeDTO getWorkType(Integer workTypeId);
}
