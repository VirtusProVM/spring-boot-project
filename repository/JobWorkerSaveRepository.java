package com.jobportal.repository;

import com.jobportal.model.JobWorkerSave;
import com.jobportal.model.PostCenter;
import com.jobportal.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobWorkerSaveRepository extends JpaRepository<JobWorkerSave, Integer> {

    public List<JobWorkerSave> findByUserId(Worker workerID);

    List<JobWorkerSave> findByJob(PostCenter job);

}
