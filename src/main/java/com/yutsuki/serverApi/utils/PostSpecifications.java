package com.yutsuki.serverApi.utils;

import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.entity.TagsPost;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;

public class PostSpecifications {
    public static Specification<Post> hasId(Long id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Post> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Post> hasContent(String content) {
        return (root, query, criteriaBuilder) -> content == null ? null : criteriaBuilder.like(root.get("content"), "%" + content + "%");
    }

    public static Specification<Post> hasTags(List<String> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return null;
            }
            Join<Post, TagsPost> tagsJoin = root.join("tags", JoinType.INNER);
            return tagsJoin.get("name").in(tags);
        };
    }

}
