package org.example.springboot.entity;

import java.util.Date;

public class UserComment {

    /** 评论 ID，主键，自增 */
    private int comment_id;
    /** 用户 ID */
    private int user_id;
    /** 目标类型，HERB=药材，ARTICLE=文章，VIDEO=视频 */
    private String target_type;
    /** 目标 ID */
    private int target_id;

    /** 父评论 ID，0 表示一级评论 */
    private int parent_id;
    /** 评论内容 */
    private String content;
    /** 状态，0 正常 1 删除，默认值为 0 */
    private Character status;
    /** 创建时间，默认值为当前时间 */
    private Date create_time;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTargetType(String targetType) {
        this.target_type = targetType;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
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
        return create_time;
    }

    public void setCreateTime(Date createTime) {
        this.create_time = createTime;
    }
}
