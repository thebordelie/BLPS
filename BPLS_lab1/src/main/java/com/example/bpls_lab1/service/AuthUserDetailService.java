package com.example.bpls_lab1.service;

import com.example.bpls_lab1.dtos.RegistrationDTO;
import com.example.bpls_lab1.dtos.UserDTO;
import com.example.bpls_lab1.model.Role;
import com.example.bpls_lab1.model.Subscription;
import com.example.bpls_lab1.model.User;
import com.example.bpls_lab1.repository.RoleRepository;
import com.example.bpls_lab1.repository.SubRepository;
import com.example.bpls_lab1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class AuthUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SubRepository subRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUser(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    private User findUser(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)));
    }

    public long authentication(String userName, String password) throws UsernameNotFoundException{
        User user = findUser(userName);
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", userName));
        return user.getId();
    }

    private Role getDefaultRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    private Subscription getDefaultSub() {
        return subRepository.findBySubscription("DEFAULT");
    }

    public void createNewUser(RegistrationDTO dto) {
        User newUser = new User();
        newUser.setUserName(dto.getUserName());
        newUser.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        newUser.setRoles(List.of(getDefaultRole()));
        newUser.setSubscription(getDefaultSub());
        userRepository.save(newUser);
    }

}
