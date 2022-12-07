package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.interfaces.IWorkTypeService;
import com.licenta.databasemicroservice.business.model.worktype.WorkTypeResponse;
import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.business.util.mapper.WorkTypeMapper;
import com.licenta.databasemicroservice.persistence.entity.WorkType;
import com.licenta.databasemicroservice.persistence.repository.WorkTypeRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WorkTypeService implements IWorkTypeService {

    @Autowired
    private WorkTypeRepository workTypeRepository;

    private final WorkTypeMapper workTypeMapper = Mappers.getMapper(WorkTypeMapper.class);

    static final String WORK_TYPE_NOT_FOUND_MESSAGE = "WorkType with id <%s> does not exist.";

    @Override
    public WorkType getWorkTypeOrElseThrowException(Integer workTypeId) {

        return workTypeRepository.findById(workTypeId).orElseThrow(
                () -> new NotFoundException(String.format(WORK_TYPE_NOT_FOUND_MESSAGE, workTypeId))
        );
    }

    @Override
    public Iterable<WorkTypeResponse> getWorkTypes() {
        return StreamSupport.stream(workTypeRepository.findAll().spliterator(), false)
                .map(workTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WorkTypeResponse getWorkType(Integer workTypeId) {
        WorkType workType = getWorkTypeOrElseThrowException(workTypeId);

        return workTypeMapper.toResponse(workType);
    }
}
