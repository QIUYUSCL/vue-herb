package org.example.springboot.entity;

import java.util.Date;

public class UserInteraction {

    /** 互动 ID，主键，自增，用于唯一标识每条互动记录 */
    private int interactionId;
    /** 执行互动操作的用户的标识 */
    private int userId;
    /** 目标类型，可取值为 HERB（药材）、ARTICLE（文章）、VIDEO（视频） */
    private String targetType;

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(int interactionId) {
        this.interactionId = interactionId;
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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 对应目标类型的具体记录的 ID */
    private int targetId;
    /** 操作类型，可取值为 LIKE（点赞）、COLLECT（收藏） */
    private String actionType;
    /** 记录互动操作发生的时间，默认值为当前时间 */
    private Date createTime;
}
