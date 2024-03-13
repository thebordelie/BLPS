package com.example.lab2bpls.service;

import com.example.lab2bpls.model.User;
import com.example.lab2bpls.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final TransactionTemplate template;
    private final UserRepository userRepository;


    public void updateUserSubscription(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(
                ("Invalid login")
        ));
        template.execute(status -> {
            userRepository.updateSub(user.getId());
            userRepository.addLog(user.getId(), new Date());
            return user.getId();
        });

    }

}
