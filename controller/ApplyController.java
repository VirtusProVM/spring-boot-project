package com.jobportal.controller;

import com.jobportal.model.*;
import com.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ApplyController {

    private final PostJobService postJobService;
    private final UsersService usersService;
    private final JobApplyService jobApplyService;
    private final JobWorkerSaveService jobWorkerSaveService;
    private final RecruiterService recruiterProfileService;
    private final WorkerService workerService;

    @Autowired
    public ApplyController(PostJobService postJobService, UsersService usersService, JobApplyService jobApplyService,
                           JobWorkerSaveService jobWorkerSaveService, RecruiterService recruiterProfileService, WorkerService workerService) {
        this.postJobService = postJobService;
        this.usersService = usersService;
        this.jobApplyService = jobApplyService;
        this.jobWorkerSaveService = jobWorkerSaveService;
        this.recruiterProfileService = recruiterProfileService;
        this.workerService = workerService;
    }

    @GetMapping("job-details-apply/{id}")
    public String displayJobDetails(@PathVariable("id") int id, Model model) {
        PostCenter jobDetails = postJobService.getOnePost(id);

        List<JobApply> jobApplies = jobApplyService.getJobCandidates(jobDetails);
        List<JobWorkerSave> workerSaves = jobWorkerSaveService.getJobCandidates(jobDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                Recruiter recruiter = recruiterProfileService.getCurrentRecruiterProfile();
                if (recruiter != null) {
                    model.addAttribute("applyList", jobApplies);
                }
            } else {
                Worker worker = workerService.getCurrentWorker();
                if (worker != null) {
                    boolean isExists = false;
                    boolean isSaved = false;
                    for (JobApply ja : jobApplies) {
                        if (ja.getUserId().getWorkerID() == worker.getWorkerID()) {
                            isExists = true;
                            break;
                        }
                    }
                    for (JobWorkerSave save : workerSaves) {
                        if (save.getWorkerID().getWorkerID() == worker.getWorkerID()) {
                            isSaved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", isExists);
                    model.addAttribute("alreadySaved", isSaved);
                }
            }
        }

        JobApply jobApply = new JobApply();
        model.addAttribute("applyJob", jobApply);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUser());
        return "job-details-page";
    }

    @PostMapping("job-details/apply/{id}")
    public String applyForJob(@PathVariable("id") int id, JobApply jobApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.getByEmail(currentUsername);
            Optional<Worker> worker = workerService.getOne(user.getUserID());
            PostCenter jobPostActivity = postJobService.getOnePost(id);
            if (worker.isPresent() && jobPostActivity != null) {
                jobApply = new JobApply();
                jobApply.setUserId(worker.get());
                jobApply.setJob(jobPostActivity);
                jobApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found");
            }
            jobApplyService.addNew(jobApply);
        }

        return "redirect:/job-board/";
    }
}
