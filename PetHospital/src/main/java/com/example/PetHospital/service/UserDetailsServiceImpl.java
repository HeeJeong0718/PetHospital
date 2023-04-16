package com.example.PetHospital.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.PetHospital.domain.Users;
import com.example.PetHospital.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
/*
@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed: No User Info"));
        return user;
    }*/

