package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.repository.StatusRepository;
import com.lawzoom.complianceservice.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Status createStatus(String status) {
        // Create a new Status object and set the name
        Status status1 = new Status();
        status1.setName(status); // Set the name from the parameter
        return statusRepository.save(status1); // Save the status to the database
    }


    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
}
