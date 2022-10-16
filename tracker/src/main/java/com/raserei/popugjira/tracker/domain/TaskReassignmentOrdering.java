package com.raserei.popugjira.tracker.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class TaskReassignmentOrdering {

    @Id
    @Getter @Setter
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    @Getter @Setter
    private Task task;

    @Column
    @Getter @Setter
    private String locked_by;
}
