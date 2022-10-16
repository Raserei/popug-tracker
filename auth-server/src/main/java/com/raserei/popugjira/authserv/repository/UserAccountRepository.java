package com.raserei.popugjira.authserv.repository;

import com.raserei.popugjira.authserv.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    Optional<UserAccount> findByPublicIdIgnoreCase(String publicId);


    Optional<UserAccount> findByUsernameIgnoreCaseAndPassword(String username, String password);

    Optional<UserAccount> findByUsername(String username);

    boolean existsByPublicIdAndIsActiveTrue(String publicId);






}