package com.jobportal.service;

import com.jobportal.model.JobWorkerSave;
import com.jobportal.model.PostCenter;
import com.jobportal.model.Worker;
import com.jobportal.repository.JobWorkerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobWorkerSaveService {

    private JobWorkerSaveRepository jobWorkerSaveRepository;

    public JobWorkerSaveService(JobWorkerSaveRepository jobWorkerSaveRepository) {
        this.jobWorkerSaveRepository = jobWorkerSaveRepository;
    }
    public List<JobWorkerSave> getCandidatesJob(Worker workerID) {
        return jobWorkerSaveRepository.findByUserId(workerID);
    }

    public List<JobWorkerSave> getJobCandidates(PostCenter jobDetails) {
        return jobWorkerSaveRepository.findByJob(jobDetails);
    }

    public void addNew(JobWorkerSave jobSeekerSave) {
        jobWorkerSaveRepository.save(jobSeekerSave);
    }
}
