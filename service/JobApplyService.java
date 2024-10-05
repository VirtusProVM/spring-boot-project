package com.jobportal.service;

import com.jobportal.model.JobApply;
import com.jobportal.model.PostCenter;
import com.jobportal.model.Worker;
import com.jobportal.repository.JobApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplyService {

    private JobApplyRepository jobApplyRepository;

    @Autowired
    public JobApplyService(JobApplyRepository jobApplyRepository) {
        this.jobApplyRepository = jobApplyRepository;
    }
    public List<JobApply> getCandidatesJobs(Worker workerID) {
        return jobApplyRepository.findByUserId(workerID);
    }

    public List<JobApply> getJobCandidates(PostCenter jobDetails) {
        return jobApplyRepository.findByJob(jobDetails);
    }

    public void addNew(JobApply jobSeekerApply) {
        jobApplyRepository.save(jobSeekerApply);
    }
}
