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
@Table(name = "tags_post")
public class TagsPost extends BaseEntity {
    @ManyToOne
    private Post post;
    private String name;
}