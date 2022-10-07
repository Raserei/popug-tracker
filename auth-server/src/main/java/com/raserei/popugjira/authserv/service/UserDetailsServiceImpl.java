package com.raserei.popugjira.authserv.service;

import java.util.Objects;

import com.raserei.popugjira.authserv.domain.UserAccount;
import com.raserei.popugjira.authserv.domain.UserDetailsImpl;
import com.raserei.popugjira.authserv.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (Objects.isNull(userAccount)) {
            throw new UsernameNotFoundException("User not found.");
        }

        return new UserDetailsImpl.Builder()
                .withUsername(userAccount.getUsername())
                .withPassword(userAccount.getPassword())
                .build();
    }

}