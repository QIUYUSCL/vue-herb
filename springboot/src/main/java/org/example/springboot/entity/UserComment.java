package org.example.springboot.entity;

import java.util.Date;

public class UserComment {

    /** 评论 ID，主键，自增 */
    private int commentId;
    /** 用户 ID */
    private int userId;
    /** 目标类型，HERB=药材，ARTICLE=文章，VIDEO=视频 */
    private String targetType;
    /** 目标 ID */
    private int targetId;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 父评论 ID，0 表示一级评论 */
    private int parentId;
    /** 评论内容 */
    private String content;
    /** 状态，0 正常 1 删除，默认值为 0 */
    private Character status;
    /** 创建时间，默认值为当前时间 */
    private Date createTime;
}
