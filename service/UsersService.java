package com.jobportal.service;

import com.jobportal.model.Recruiter;
import com.jobportal.model.Users;
import com.jobportal.model.Worker;
import com.jobportal.repository.RecruiterRepository;
import com.jobportal.repository.UsersRepository;
import com.jobportal.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private WorkerRepository workerRepository;
    private RecruiterRepository recruiterRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, WorkerRepository workerRepository, RecruiterRepository recruiterRepository,
                        PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.workerRepository = workerRepository;
        this.recruiterRepository = recruiterRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users createNewUser(Users users) {
        users.setActive(true);
        users.setRegisterDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeID = users.getWorkerTypeID().getTypeID();

        if(userTypeID == 1) {
            recruiterRepository.save(new Recruiter(savedUser));
        } else {
            workerRepository.save(new Worker(savedUser));
        }
        return savedUser;

    }

    public Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserID();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                Recruiter recruiterProfile = recruiterRepository.findById(userId).orElse(new Recruiter());
                return recruiterProfile;
            } else {
                Worker worker = workerRepository.findById(userId).orElse(new Worker());
                return worker;
            }
        }

        return null;
    }

    public Users getCandidate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            return user;
        }

        return null;
    }

    public Users getByEmail(String currentUsername) {
        return usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not " +
                "found"));
    }
}
