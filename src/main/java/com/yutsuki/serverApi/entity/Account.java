package com.yutsuki.serverApi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "account")
public class Account extends BaseEntity implements Serializable {
    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String userName;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String mobile;

//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    private List<Post> posts;
}
