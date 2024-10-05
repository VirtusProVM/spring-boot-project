package com.jobportal.repository;

import com.jobportal.model.JobApply;
import com.jobportal.model.PostCenter;
import com.jobportal.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplyRepository extends JpaRepository<JobApply, Integer> {

    List<JobApply> findByUserId(Worker workerID);

    List<JobApply> findByJob(PostCenter postID);
}
