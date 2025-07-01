package org.example.springboot.entity;

import java.util.Date;

public class VideoInfo {

    private Integer video_id;

    private String title;
    /**
     * 对视频内容的简要描述
     */
    private String description;
    /**
     * 视频文件的链接地址
     */
    private String video_url;
    /**
     * 视频的封面图片链接
     */
    private String cover_image;
    /**
     * 视频的时长，以秒为单位
     */
    private Integer duration;
    /**
     * 视频所属的分类
     */
    private String category;
    /**
     * 视频的状态，0 表示正常，1 表示停用
     */
    private Character status;
    /**
     * 视频的浏览次数
     */
    private Integer views;
    /**
     * 视频的点赞数量
     */
    private Integer likes;
    /**
     * 视频的评论数量
     */
    private Integer comments;
    /**
     * 用于视频排序的字段
     */
    private Integer sort;
    /**
     * 视频记录创建的时间，默认值为当前时间
     */
    private Date createTime;

    public Integer getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
