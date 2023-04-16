package com.example.PetHospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.exception.CUserNotFoundException;
import com.example.PetHospital.repository.UsersRepository;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository UserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return UserRepository.findById(Long.parseLong(userPk))
                .orElseThrow(CUserNotFoundException::new);
    }
}
