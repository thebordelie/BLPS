package com.example.lab2bpls.jwt;

import com.example.lab2bpls.model.JwtAuthentication;
import com.example.lab2bpls.model.Role;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        List roles = claims.get("roles", List.class);
        LinkedHashMap<Long, String> role = (LinkedHashMap<Long, String>) roles.get(0);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1, role.get("name")));
        return roleSet;
    }

    public static Authentication getJwtAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
