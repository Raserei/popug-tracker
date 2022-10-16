package com.raserei.popugjira.tracker.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRole {


    @Id
    @Getter
    @Setter
    private String id;


    @Getter
    @Setter
    @Column
    private String publicId;

    @Getter
    @Setter
    @Column
    private String name;
}
