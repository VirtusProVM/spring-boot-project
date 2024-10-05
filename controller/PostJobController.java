package com.jobportal.controller;

import com.jobportal.model.*;
import com.jobportal.service.JobApplyService;
import com.jobportal.service.JobWorkerSaveService;
import com.jobportal.service.PostJobService;
import com.jobportal.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class PostJobController {

    private final UsersService usersService;
    private final PostJobService postJobService;
    private final JobApplyService jobApplyService;
    private final JobWorkerSaveService jobWorkerSaveService;

    @Autowired
    public PostJobController(UsersService usersService, PostJobService postJobService, JobApplyService jobApplyService,
                             JobWorkerSaveService jobWorkerSaveService) {
        this.usersService = usersService;
        this.postJobService = postJobService;
        this.jobApplyService = jobApplyService;
        this.jobWorkerSaveService = jobWorkerSaveService;
    }

    @GetMapping("/job-board/")
    public String searchForJobs(Model model, @RequestParam(value = "job", required = false) String job,
                                @RequestParam(value = "location", required = false) String location,
                                @RequestParam(value = "partTime", required = false) String partTime,
                                @RequestParam(value = "fullTime", required = false) String fullTime,
                                @RequestParam(value = "freelance", required = false) String freelance,
                                @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
                                @RequestParam(value = "officeOnly", required = false) String officeOnly,
                                @RequestParam(value = "partialRemote", required = false) String partialRemote,
                                @RequestParam(value = "today", required = false) boolean today,
                                @RequestParam(value = "days7", required = false) boolean days7,
                                @RequestParam(value = "days30", required = false) boolean days30) {

        model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
        model.addAttribute("fullTime", Objects.equals(partTime, "Full-Time"));
        model.addAttribute("freelance", Objects.equals(partTime, "Freelance"));

        model.addAttribute("remoteOnly", Objects.equals(partTime, "Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(partTime, "Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partTime, "Partial-Remote"));

        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<PostCenter> jobPost = null;
        boolean dateSearchFlag = true;
        boolean remote = true;
        boolean type = true;

        if (days30) {
            searchDate = LocalDate.now().minusDays(30);
        } else if (days7) {
            searchDate = LocalDate.now().minusDays(7);
        } else if (today) {
            searchDate = LocalDate.now();
        } else {
            dateSearchFlag = false;
        }

        if (partTime == null && fullTime == null && freelance == null) {
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freelance = "Freelance";
            remote = false;
        }

        if (officeOnly == null && remoteOnly == null && partialRemote == null) {
            officeOnly = "Office-Only";
            remoteOnly = "Remote-Only";
            partialRemote = "Partial-Remote";
            type = false;
        }

        if (!dateSearchFlag && !remote && !type && !StringUtils.hasText(job) && !StringUtils.hasText(location)) {
            jobPost = postJobService.getAll();
        } else {
            jobPost = postJobService.search(job, location, Arrays.asList(partTime, fullTime, freelance),
                    Arrays.asList(remoteOnly, officeOnly, partialRemote), searchDate);
        }

        Object currentUserProfile = usersService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                List<RecruiterDTO> recruiterJobs = postJobService.getRecruiterJobs(((Recruiter) currentUserProfile).getRecruiterID());
                model.addAttribute("jobPost", recruiterJobs);
            } else {
                List<JobApply> jobApplyList = jobApplyService.getCandidatesJobs((Worker) currentUserProfile);
                List<JobWorkerSave> jobWorkerList = jobWorkerSaveService.getCandidatesJob((Worker) currentUserProfile);

                boolean exist;
                boolean saved;

                for (PostCenter postCenter : jobPost) {
                    exist = false;
                    saved = false;
                    for (JobApply jobApply : jobApplyList) {
                        if (Objects.equals(postCenter.getPostID(), jobApply.getJob().getPostID())) {
                            postCenter.setIsActive(true);
                            exist = true;
                            break;
                        }
                    }

                    for (JobWorkerSave jobSave : jobWorkerList) {
                        if (Objects.equals(postCenter.getPostID(), jobSave.getJob().getPostID())) {
                            postCenter.setIsSaved(true);
                            saved = true;
                            break;
                        }
                    }

                    if (!exist) {
                        postCenter.setIsActive(false);
                    }
                    if (!saved) {
                        postCenter.setIsSaved(false);
                    }

                    model.addAttribute("jobPost", jobPost);

                }
            }
        }

        model.addAttribute("user", currentUserProfile);

        return "job-board";
    }

    @GetMapping("search/")
    public String globalSearch(Model model,
                               @RequestParam(value = "job", required = false) String job,
                               @RequestParam(value = "location", required = false) String location,
                               @RequestParam(value = "partTime", required = false) String partTime,
                               @RequestParam(value = "fullTime", required = false) String fullTime,
                               @RequestParam(value = "freelance", required = false) String freelance,
                               @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
                               @RequestParam(value = "officeOnly", required = false) String officeOnly,
                               @RequestParam(value = "partialRemote", required = false) String partialRemote,
                               @RequestParam(value = "today", required = false) boolean today,
                               @RequestParam(value = "days7", required = false) boolean days7,
                               @RequestParam(value = "days30", required = false) boolean days30) {

        model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
        model.addAttribute("fullTime", Objects.equals(partTime, "Full-Time"));
        model.addAttribute("freelance", Objects.equals(partTime, "Freelance"));

        model.addAttribute("remoteOnly", Objects.equals(partTime, "Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(partTime, "Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partTime, "Partial-Remote"));

        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<PostCenter> jobPost = null;
        boolean dateSearchFlag = true;
        boolean remote = true;
        boolean type = true;

        if (days30) {
            searchDate = LocalDate.now().minusDays(30);
        } else if (days7) {
            searchDate = LocalDate.now().minusDays(7);
        } else if (today) {
            searchDate = LocalDate.now();
        } else {
            dateSearchFlag = false;
        }

        if (partTime == null && fullTime == null && freelance == null) {
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freelance = "Freelance";
            remote = false;
        }

        if (officeOnly == null && remoteOnly == null && partialRemote == null) {
            officeOnly = "Office-Only";
            remoteOnly = "Remote-Only";
            partialRemote = "Partial-Remote";
            type = false;
        }

        if (!dateSearchFlag && !remote && !type && !StringUtils.hasText(job) && !StringUtils.hasText(location)) {
            jobPost = postJobService.getAll();
        } else {
            jobPost = postJobService.search(job, location, Arrays.asList(partTime, fullTime, freelance),
                    Arrays.asList(remoteOnly, officeOnly, partialRemote), searchDate);
        }

        model.addAttribute("jobPost", jobPost);
        return "search";
    }

    @GetMapping("/job-board/add")
    public String addJobs(Model model) {
        model.addAttribute("postCenter", new PostCenter());
        model.addAttribute("user", usersService.getCurrentUser());
        return "add-jobs";
    }

    @PostMapping("/job-board/addNew")
    public String addNewJob(PostCenter postCenter, Model model) {

        Users user = usersService.getCandidate();
        if (user != null) {
            postCenter.setUserID(user);
        }
        postCenter.setPostedDate(new Date());
        model.addAttribute("postCenter", postCenter);
        PostCenter center = postJobService.addNewPost(postCenter);
        return "redirect:/job-board/";
    }

    @GetMapping("job-board/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model) {

        PostCenter postCenter = postJobService.getOnePost(id);
        model.addAttribute("postCenter", postCenter);
        model.addAttribute("user", usersService.getCurrentUser());
        return "add-jobs";
    }
}
