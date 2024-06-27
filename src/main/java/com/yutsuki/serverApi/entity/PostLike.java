package com.yutsuki.serverApi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "post_like")
public class PostLike extends BaseEntity implements Serializable {
    @ManyToOne
    private Account account;
    @ManyToOne
    private Post post;
}