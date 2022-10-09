package com.raserei.popugjira.tracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserAccount {

    @Id
    private String id;

    @Column
    private String publicId;

    @Column
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "user_userrole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private List<UserRole> roles;
}
