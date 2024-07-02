package com.yutsuki.serverApi.utils;

import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.entity.TagsPost;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PostSpecifications {
    public static Specification<Post> hasId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(id)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    public static Specification<Post> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(title)) {
                return null;
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Post> hasContent(String content) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(content)) {
                return null;
            }
            return criteriaBuilder.like(root.get("content"), "%" + content + "%");
        };
    }

    public static Specification<Post> hasTags(List<String> tags) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(tags) | tags.isEmpty()) {
                return null;
            }
            Join<Post, TagsPost> tagsJoin = root.join("tags", JoinType.INNER);
            return tagsJoin.get("name").in(tags);
        };
    }

    public static Specification<Post> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(status)) {
                return null;
            }
            Optional<PostStatus> postStatus = PostStatus.find(status);
            if (!postStatus.isPresent()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}
