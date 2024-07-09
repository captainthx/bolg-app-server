package com.yutsuki.serverApi.exception;

public class PostException extends BaseException {
    public PostException(String message) {
        super(message);
    }

    public static PostException invalidPostTitle() {
        return new PostException("Invalid post title");
    }

    public static PostException invalidPostContent() {
        return new PostException("Invalid post content");
    }

    public static PostException invalidPostStatus() {
        return new PostException("Invalid post status");
    }

    public static PostException postNotFound() {
        return new PostException("Post not found");
    }

    public static PostException invalidComment() {
        return new PostException("Invalid comment");
    }

    public static PostException invalidPostId() {
        return new PostException("Invalid post id");
    }

    public static PostException postLiked() {
        return new PostException("Post already liked");
    }

    public static PostException postAlreadyFavorite() {
        return new PostException("Post already favorite");
    }
}
