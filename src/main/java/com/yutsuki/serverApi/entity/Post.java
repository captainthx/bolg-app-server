package com.yutsuki.serverApi.entity;

import com.yutsuki.serverApi.common.PostStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @ManyToOne
    private Account account;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;
    private Integer like;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}