package org.example.springboot.entity;

import java.util.Date;

public class UserInteraction {

    /** 互动 ID，主键，自增，用于唯一标识每条互动记录 */
    private int interaction_id;
    /** 执行互动操作的用户的标识 */
    private int user_id;
    /** 目标类型，可取值为 HERB（药材）、ARTICLE（文章）、VIDEO（视频） */
    private String target_type;


    /** 对应目标类型的具体记录的 ID */
    private int target_id;
    /** 操作类型，可取值为 LIKE（点赞）、COLLECT（收藏） */
    private String action_type;
    /** 记录互动操作发生的时间，默认值为当前时间 */
    private Date create_time;


    public int getInteraction_id() {
        return interaction_id;
    }

    public void setInteraction_id(int interaction_id) {
        this.interaction_id = interaction_id;
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

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
