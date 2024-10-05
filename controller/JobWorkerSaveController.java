package com.jobportal.controller;

import com.jobportal.model.JobWorkerSave;
import com.jobportal.model.PostCenter;
import com.jobportal.model.Users;
import com.jobportal.model.Worker;
import com.jobportal.service.JobWorkerSaveService;
import com.jobportal.service.PostJobService;
import com.jobportal.service.UsersService;
import com.jobportal.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobWorkerSaveController {

    private final UsersService usersService;
    private final WorkerService workerService;
    private final PostJobService postJobService;
    private final JobWorkerSaveService jobWorkerSaveService;

    @Autowired
    public JobWorkerSaveController(UsersService usersService, WorkerService workerService, PostJobService postJobService,
                                   JobWorkerSaveService jobWorkerSaveService) {
        this.usersService = usersService;
        this.workerService = workerService;
        this.postJobService = postJobService;
        this.jobWorkerSaveService = jobWorkerSaveService;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id, JobWorkerSave jobWorkerSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.getByEmail(currentUsername);
            Optional<Worker> workerOptional = workerService.getOne(user.getUserID());
            PostCenter postCenter = postJobService.getOnePost(id);
            if (workerOptional.isPresent() && postCenter != null) {
                jobWorkerSave.setJob(postCenter);
                jobWorkerSave.setWorkerID(workerOptional.get());
            } else {
                throw new RuntimeException("User not found");
            }
            jobWorkerSaveService.addNew(jobWorkerSave);
        }
        return "redirect:/job-board/";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<PostCenter> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUser();

        List<JobWorkerSave> jobWorkerSaves = jobWorkerSaveService.getCandidatesJob((Worker) currentUserProfile);
        for (JobWorkerSave jobWorkerSave : jobWorkerSaves) {
            jobPost.add(jobWorkerSave.getJob());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}
