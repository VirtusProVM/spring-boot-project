package com.jobportal.service;

import com.jobportal.model.WorkerType;
import com.jobportal.repository.WorkerTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerTypeService {

    private WorkerTypeRepository workerTypeRepository;

    public WorkerTypeService(WorkerTypeRepository workerTypeRepository) {
        this.workerTypeRepository = workerTypeRepository;
    }

    public List<WorkerType> getAll() {
        return workerTypeRepository.findAll();
    }
}
