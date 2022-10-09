package com.raserei.popugjira.tracker.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserAccount,String> {

    @Query(nativeQuery=true, value="SELECT *  FROM user_account " +
            "WHERE user_role not in ('ADMIN', 'MANAGER')" +
            " ORDER BY random() LIMIT 1")
    Optional<UserAccount> getRandomAccount();
}
