package org.example.springboot.entity;

import java.util.Date;

public class UserHistory {

    /** 历史记录 ID，主键，自增 */
    private int historyId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 用户 ID */
    private int userId;
    /** 目标类型，HERB=药材，ARTICLE=文章，VIDEO=视频 */
    private String targetType;
    /** 目标 ID */
    private Long targetId;
    /** 浏览时间，默认值为当前时间 */
    private Date createTime;
}
