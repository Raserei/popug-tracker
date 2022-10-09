package com.raserei.popugjira.authserv.service;

import com.raserei.popugjira.authserv.domain.UserAccount;
import com.raserei.popugjira.authserv.domain.UserAccountRole;
import com.raserei.popugjira.authserv.event.EventProducer;
import com.raserei.popugjira.authserv.event.UserCreatedEvent;
import com.raserei.popugjira.authserv.event.UserDeletedEvent;
import com.raserei.popugjira.authserv.exception.UserNotFoundException;
import com.raserei.popugjira.authserv.repository.UserAccountRepository;
import com.raserei.popugjira.authserv.repository.UserRoleRepository;
import com.raserei.popugjira.authserv.rest.UserCreationDto;
import com.raserei.popugjira.authserv.rest.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserAccountRepository userAccountRepository;

    public final UserRoleRepository userRoleRepository;

    public final EventProducer messageClient;

    public UserAccount getUser(String email, String password) throws IllegalAccessException {
        return userAccountRepository.findByEmailIgnoreCaseAndPassword(email, password).orElseThrow(IllegalAccessException::new);
    }

    public UserDto getUser(String publicId) throws UserNotFoundException {
        UserAccount userAccount= userAccountRepository.findByPublicIdIgnoreCase(publicId).orElseThrow(UserNotFoundException::new);
        return userDtoToAccountMapper().apply(userAccount);
    }

    public UserAccount getUserAccount(String publicId) throws UserNotFoundException {
        return userAccountRepository.findByPublicIdIgnoreCase(publicId).orElseThrow(UserNotFoundException::new);
    }

    public List<UserDto> getUserList() {
        List<UserAccount> userAccountList= userAccountRepository.findAll();
        return userAccountList.stream()
                .map(userDtoToAccountMapper())
                .toList();
    }
    @Transactional
    public void createUser(UserCreationDto userCreationDto) {
        Set<UserAccountRole> userAccountRoles =
                userCreationDto.getUserRoles()
                        .stream()
                        .map(role -> userRoleRepository.findById(role).orElseThrow(IllegalArgumentException::new))
                        .collect(Collectors.toSet());
        UserAccount userAccount = new UserAccount();
        userAccount.setId(UUID.randomUUID().toString());
        userAccount.setUsername(userCreationDto.getUsername());
        userAccount.setPassword(userCreationDto.getPassword());
        userAccount.setRoles(userAccountRoles);
        userAccount.setPublicId(UUID.randomUUID().toString());
        userAccountRepository.save(userAccount);
        messageClient.sendUserCreatedEvent(new UserCreatedEvent(userAccount.getPublicId(),
                userAccount.getRoles()
                        .stream().map(UserAccountRole::getRole)
                        .toList()
                ));
    }

    @Transactional
    public void deleteUser(String userId) throws UserNotFoundException {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userAccount.setIsActive(false);
        userAccountRepository.save(userAccount);
        messageClient.sendUserDeletedEvent(new UserDeletedEvent(userAccount.getPublicId()));
    }

    private Function<UserAccount, UserDto> userDtoToAccountMapper() {
        return userAccount -> new UserDto(userAccount.getPublicId(), userAccount.getUsername());
    }

    public boolean validateUser(String publicId) {
        return userAccountRepository.existsByPublicIdAndIsActiveTrue(publicId);
    }
}
