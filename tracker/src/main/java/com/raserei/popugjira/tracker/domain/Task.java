package com.raserei.popugjira.tracker.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @Getter @Setter
    private String id;

    @Column
    @Getter @Setter
    private String publicId;

    @Column
    @Getter @Setter
    private String shortDescription;

    @Column
    @Getter @Setter
    private String longDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    @Getter @Setter
    private UserAccount assignee;

    @Column
    @Getter @Setter
    private TaskStatus status;

}
