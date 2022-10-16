package com.raserei.popugjira.tracker.service;

import com.raserei.popugjira.tracker.domain.UserAccount;
import com.raserei.popugjira.tracker.domain.UserRepository;
import com.raserei.popugjira.tracker.domain.UserRole;
import com.raserei.popugjira.tracker.domain.UserRoleRepository;
import com.raserei.popugjira.tracker.exception.NoEmployeesAvailable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    public void createUser(String publicId, List<String> roles) {
        List<UserRole> userRoleList = roles.stream()
                .map(userRoleRepository::findByNameIgnoreCase)
                .toList();
        UserAccount userAccount = new UserAccount(UUID.randomUUID().toString(), publicId, false, userRoleList);
        userRepository.save(userAccount);
    }

    public UserAccount getRandomUserAccount() throws NoEmployeesAvailable {
        return userRepository.getRandomAccount().orElseThrow(NoEmployeesAvailable::new);
    }

}
