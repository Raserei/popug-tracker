package com.raserei.popugjira.tracker.domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task,String> {

    Optional<Task> findByPublicId(String publicId);

}
