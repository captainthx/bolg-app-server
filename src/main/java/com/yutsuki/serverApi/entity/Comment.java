package com.yutsuki.serverApi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    @ManyToOne
    private Account account;
    @ManyToOne
    private Post post;
    @Column(columnDefinition = "TEXT")
    private String comment;
}