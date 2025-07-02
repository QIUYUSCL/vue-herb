package org.example.springboot.entity;

import java.util.Date;

public class DailyLearning {

    // 文章 ID，主键，自增
    private Integer article_id;
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 封面图片地址
    private String cover_image;
    // 文章分类
    private String category;
    // 文章状态（0 发布 1 草稿），默认值为 0
    private Character status;
    // 文章发布时间
    private Date publish_time;
    // 文章浏览量，默认值为 0
    private Integer views;
    // 文章点赞数，默认值为 0
    private Integer likes;

    private int collections;
    // 文章评论数，默认值为 0
    private Integer comments;
    // 文章排序，默认值为 0
    private Integer sort;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    // 文章创建时间，默认值为当前时间
    private Date create_time;


    public int getCollections() {
        return collections;
    }

    public void setCollections(int collections) {
        this.collections = collections;
    }
}
