package com.jobportal.controller;

import com.jobportal.helper.FileDownloadManagement;
import com.jobportal.helper.FileManagementSystem;
import com.jobportal.model.Skills;
import com.jobportal.model.Users;
import com.jobportal.model.Worker;
import com.jobportal.repository.UsersRepository;
import com.jobportal.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/workerProfilePage")
public class WorkerController {

    private WorkerService workerProfileService;

    private UsersRepository usersRepository;

    @Autowired
    public WorkerController(WorkerService workerProfileService, UsersRepository usersRepository) {
        this.workerProfileService = workerProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String workerProfilePage(Model model) {
        Worker worker = new Worker();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            Optional<Worker> workerProfile = workerProfileService.getOne(user.getUserID());
            if (workerProfile.isPresent()) {
                worker = workerProfile.get();
                if (worker.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    worker.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("profile", worker);
        }

        return "worker";
    }

    @PostMapping("/addNewCandidate")
    public String addNewCandidate(Worker worker,
                         @RequestParam("image") MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf,
                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            worker.setUsers(user);
            worker.setWorkerID(user.getUserID());
        }

        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("profile", worker);
        model.addAttribute("skills", skillsList);

        for (Skills skills : worker.getSkills()) {
            skills.setWorker(worker);
        }

        String imageName = "";
        String resumeName = "";

        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            worker.setProfilePhoto(imageName);
        }

        if (!Objects.equals(pdf.getOriginalFilename(), "")) {
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            worker.setResume(resumeName);
        }

        Worker seekerProfile = workerProfileService.addNewWorker(worker);

        try {
            String uploadDir = "photos/candidate/" + worker.getWorkerID();
            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileManagementSystem.saveFile(uploadDir, imageName, image);
            }
            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileManagementSystem.saveFile(uploadDir, resumeName, pdf);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return "redirect:/job-board/";
    }

    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") int id, Model model) {

        Optional<Worker> worker = workerProfileService.getOne(id);
        model.addAttribute("profile", worker.get());
        return "worker";
    }

    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "userID") String userId) {

        FileDownloadManagement downloadUtil = new FileDownloadManagement();
        Resource resource = null;

        try {
            resource = downloadUtil.getFileAsResourse("photos/candidate/" + userId, fileName);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }
}
