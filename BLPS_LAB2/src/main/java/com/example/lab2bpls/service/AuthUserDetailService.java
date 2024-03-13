package com.example.lab2bpls.service;

import com.example.lab2bpls.dtos.JwtRequest;
import com.example.lab2bpls.dtos.JwtResponse;
import com.example.lab2bpls.jwt.JwtProvider;
import com.example.lab2bpls.model.Role;
import com.example.lab2bpls.model.Subscription;
import com.example.lab2bpls.model.User;
import com.example.lab2bpls.repository.RoleRepository;
import com.example.lab2bpls.repository.SubRepository;
import com.example.lab2bpls.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class AuthUserDetailService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SubRepository subRepository;
    private final HashMap<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findUser(username);
//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(),
//                user.getPassword(),
//                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
//        );
//    }

    private User findUser(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)));
    }

    public JwtResponse authentication(String userName, String password) throws UsernameNotFoundException {
        User user = findUser(userName);
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword()))
            throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", userName));
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    private Role getDefaultRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    private Subscription getDefaultSub() {
        return subRepository.findBySubscription("DEFAULT");
    }
//
    public void createNewUser(JwtRequest dto) {
        User newUser = new User();
        newUser.setUserName(dto.getLogin());
        newUser.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        newUser.setRoles(List.of(getDefaultRole()));
        newUser.setSubscription(getDefaultSub());
        userRepository.save(newUser);
    }

}
