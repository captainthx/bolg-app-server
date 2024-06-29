package com.yutsuki.serverApi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity implements Serializable {
    @ManyToOne
    private Account account;

    @ManyToOne
    private Post post;

    @Column(columnDefinition = "TEXT COLLATE utf8mb4_general_ci")
    private String comment;
}