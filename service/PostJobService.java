package com.jobportal.service;

import com.jobportal.helper.RecruiterHelper;
import com.jobportal.model.Company;
import com.jobportal.model.Location;
import com.jobportal.model.PostCenter;
import com.jobportal.model.RecruiterDTO;
import com.jobportal.repository.PostCenterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostJobService {

    private final PostCenterRepository postCenterRepository;

    public PostJobService(PostCenterRepository postCenterRepository) {
        this.postCenterRepository = postCenterRepository;
    }

    public List<PostCenter> getAll() {
        return postCenterRepository.findAll();
    }

    public List<PostCenter> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate) ? postCenterRepository.searchJobsWithoutDate(job, location, remote,type) :
                postCenterRepository.searchJobs(job, location, remote, type, searchDate);
    }

    public List<RecruiterDTO> getRecruiterJobs(int recruiterID) {
        List<RecruiterHelper> recruiterJobsDtos = postCenterRepository.getRecruiterJobs(recruiterID);

        List<RecruiterDTO> recruiterJobsDtoList = new ArrayList<>();

        for (RecruiterHelper rec : recruiterJobsDtos) {
            Location loc = new Location(rec.getLocationID(), rec.getCity(), rec.getState(), rec.getCountry());
            Company comp = new Company(rec.getCompanyID(), rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterDTO(rec.getTotalCandidates(), rec.getPostID(),
                    rec.getJobTitle(), loc, comp));
        }
        return recruiterJobsDtoList;
    }

    public PostCenter addNewPost(PostCenter postCenter) {
        return postCenterRepository.save(postCenter);
    }

    public PostCenter getOnePost(int id) {
        return postCenterRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));
    }
}
