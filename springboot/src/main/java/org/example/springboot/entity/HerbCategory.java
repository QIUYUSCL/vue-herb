package org.example.springboot.entity;

import java.util.Date;

public class HerbCategory {
    /** 类别 ID */
    private Integer category_id;
    /** 类别名称 */
    private String category_name;
    /** 父类别 ID（0 表示顶级） */
    private Long parent_id;
    /** 类别描述 */
    private String description;
    /** 状态（0 正常 1 停用） */
    private String status;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private Date create_time;

}
