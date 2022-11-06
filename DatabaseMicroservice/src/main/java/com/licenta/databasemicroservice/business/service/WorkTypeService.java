package com.licenta.databasemicroservice.business.service;

import com.licenta.databasemicroservice.business.util.exception.NotFoundException;
import com.licenta.databasemicroservice.persistence.entity.WorkType;
import com.licenta.databasemicroservice.persistence.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkTypeService {

    @Autowired
    private WorkTypeRepository workTypeRepository;

    static final String WORK_TYPE_NOT_FOUND_MESSAGE = "WorkType with id <%s> does not exist.";

    public WorkType getWorkTypeOrElseThrowException(Integer workTypeId) {

        return workTypeRepository.findById(workTypeId).orElseThrow(
                () -> new NotFoundException(String.format(WORK_TYPE_NOT_FOUND_MESSAGE, workTypeId))
        );
    }
}
