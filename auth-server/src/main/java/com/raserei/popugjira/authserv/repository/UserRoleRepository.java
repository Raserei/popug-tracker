package com.raserei.popugjira.authserv.repository;

import com.raserei.popugjira.authserv.domain.UserAccountRole;
import org.apache.coyote.ajp.AjpAprProtocol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserAccountRole, String> {
}
