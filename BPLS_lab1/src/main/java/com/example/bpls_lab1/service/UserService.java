package com.example.bpls_lab1.service;

import com.example.bpls_lab1.model.User;
import com.example.bpls_lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateUserSubscription(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(
                ("Invalid login")
        ));
        userRepository.updateSub(user.getId());
    }
}
