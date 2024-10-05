package com.jobportal.service;

import com.jobportal.model.Recruiter;
import com.jobportal.model.Users;
import com.jobportal.repository.RecruiterRepository;
import com.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterService {

    private final RecruiterRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public RecruiterService(RecruiterRepository recruiterProfileRepository, UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository = usersRepository;
    }


    public Optional<Recruiter> getOneRecruiter(int userID) {
        return recruiterProfileRepository.findById(userID);
    }

    public Recruiter addNewRecruiter(Recruiter recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public Recruiter getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Optional<Recruiter> recruiterProfile = getOneRecruiter(users.getUserID());
            return recruiterProfile.orElse(null);
        } else
            return null;
    }

}
