package com.raserei.popugjira.tracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskReassignmentOrderingRepository extends JpaRepository<TaskReassignmentOrdering, Long> {

    List<TaskReassignmentOrdering> findByLocked_byNullLimitedTo(int limit);

    @Query(nativeQuery = true, value = "insert into task_shuffle_ordering (task_id, locked_by) " +
            "select id, true from task where status <> 'CLOSED'")
    void createOpenTaskReassignments();

}
