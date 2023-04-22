package com.licenta.databasemicroservice.presentation.controller;

import com.licenta.databasemicroservice.business.interfaces.IWorkTypeService;
import com.licenta.databasemicroservice.business.model.WorkTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value="/api/work-types")
public class WorkTypeController {

    @Autowired
    private IWorkTypeService workTypeService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<WorkTypeDTO> getAllWorkTypes() {

        return workTypeService.getWorkTypes();
    }

    @RequestMapping(value="/{workTypeId}", method=RequestMethod.GET)
    public WorkTypeDTO getWorkType(@Min(1) @PathVariable Integer workTypeId) {

        return workTypeService.getWorkType(workTypeId);
    }
}
