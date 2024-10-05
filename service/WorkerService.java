package com.jobportal.service;

import com.jobportal.model.Users;
import com.jobportal.model.Worker;
import com.jobportal.repository.UsersRepository;
import com.jobportal.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, UsersRepository usersRepository) {
        this.workerRepository = workerRepository;
        this.usersRepository = usersRepository;
    }


    public Optional<Worker> getOne(int userID) {
        return workerRepository.findById(userID);
    }

    public Worker addNewWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public Worker getCurrentWorker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Optional<Worker> seekerProfile = getOne(users.getUserID());
            return seekerProfile.orElse(null);
        } else return null;
    }

}
