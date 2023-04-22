package com.licenta.databasemicroservice.business.util.mapper;

import com.licenta.databasemicroservice.business.model.WorkTypeDTO;
import com.licenta.databasemicroservice.persistence.entity.WorkType;
import org.mapstruct.Mapper;

@Mapper
public interface WorkTypeMapper {

    WorkTypeDTO toResponse(WorkType workType);
}
