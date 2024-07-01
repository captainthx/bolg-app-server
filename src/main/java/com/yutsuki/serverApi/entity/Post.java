package com.yutsuki.serverApi.entity;

import com.yutsuki.serverApi.common.PostStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "post")
public class Post extends BaseEntity implements Serializable {
    @ManyToOne
    private Account account;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT COLLATE utf8mb4_general_ci", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Column(columnDefinition = "Bigint default 0")
    private int likeCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<TagsPost> tags;
}