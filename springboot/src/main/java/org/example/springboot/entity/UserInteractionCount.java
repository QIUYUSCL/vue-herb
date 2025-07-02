package org.example.springboot.entity;

public class UserInteractionCount {

    private int likeCount;
    private int collectCount;
    private int commentCount;

    public UserInteractionCount(int likeCount, int collectCount, int commentCount) {
        this.likeCount = likeCount;
        this.collectCount = collectCount;
        this.commentCount = commentCount;
    }


    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
