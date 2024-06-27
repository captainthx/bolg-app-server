package com.yutsuki.serverApi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "post_like")
public class PostLike extends BaseEntity {
    @ManyToOne
    private Account account;
    @ManyToOne
    private Post post;
}