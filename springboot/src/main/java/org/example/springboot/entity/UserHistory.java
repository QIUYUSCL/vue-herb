package org.example.springboot.entity;

import java.util.Date;

public class UserHistory {


    /** 用户 ID */
    private int user_id;
    /** 目标类型，HERB=药材，ARTICLE=文章，VIDEO=视频 */
    private String target_type;
    /** 目标 ID */
    private int target_id;
    /** 浏览时间，默认值为当前时间 */
    private Date create_time;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String targetType) {
        this.target_type = targetType;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public Date getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Date createTime) {
        this.create_time = createTime;
    }
}
