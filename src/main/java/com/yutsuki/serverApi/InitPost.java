package com.yutsuki.serverApi;

import com.github.javafaker.Faker;
import com.yutsuki.serverApi.common.PostStatus;
import com.yutsuki.serverApi.entity.Post;
import com.yutsuki.serverApi.entity.TagsPost;
import com.yutsuki.serverApi.repository.PostRepository;
import com.yutsuki.serverApi.repository.TagsPostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("init")
@Slf4j
@AllArgsConstructor
@Profile("default")
public class InitPost {
    private final PostRepository postRepository;
    private final TagsPostRepository tagsPostRepository;

    @PostConstruct()
    public void createInitPost() throws Exception {
        log.info("init post data start");
        Faker faker = new Faker();
        faker.job().title();
        List<String> tags = Arrays.asList(faker.job().title(), faker.animal().name());
        long count = postRepository.count();
        if (count != 0) {
            return;
        }
        Set<Post> posts = new HashSet<>();
        Set<TagsPost> tagsPosts = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            log.info("create init post");
            Post initPost = new Post();
            initPost.setTitle(faker.book().title());
            initPost.setPostImage("https://images.unsplash.com/photo-1552581234-26160f608093?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1000&q=80");
            initPost.setContent(faker.lorem().paragraph());
            initPost.setStatus(PostStatus.PUBLISH);
            posts.add(initPost);
        }
        List<Post> savedPosts = postRepository.saveAll(posts);
        savedPosts.forEach(post -> {
            tags.forEach(tag -> {
                TagsPost tagsPost = new TagsPost();
                tagsPost.setPost(post);
                tagsPost.setName(tag);
                tagsPosts.add(tagsPost);
            });
        });
        tagsPostRepository.saveAll(tagsPosts);
    }
}
