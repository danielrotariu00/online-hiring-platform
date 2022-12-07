package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.worktype.WorkTypeResponse;
import com.licenta.databasemicroservice.persistence.entity.WorkType;
import org.mapstruct.Mapper;

@Mapper
public interface WorkTypeMapper {

    WorkTypeResponse toResponse(WorkType workType);
}
