package com.yutsuki.serverApi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "favorite_post")
public class FavoritePost extends BaseEntity {
    @ManyToOne
    private Post post;
    @ManyToOne
    private Account account;
}