package com.raserei.popugjira.tracker.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {


    UserRole findByNameIgnoreCase(@NonNull String name);

}
