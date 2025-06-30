package org.example.springboot.entity;

import java.util.Date;
import java.util.List;

/**
 * 药材信息实体类
 */
public class HerbInfo {
    /** 药材 ID */
    private Integer herb_id;
    /** 药材名称 */
    private String herb_name;
    /** 类别 ID */
    private Integer category_id;
    /** 拼音 */
    private String pinyin;
    /** 别名 */
    private String alias;
    /** 性味 */
    private String property;
    /** 归经 */
    private String meridian;
    /** 功效 */
    private String efficacy;
    /** 主治 */
    private String indications;
    /** 用法用量 */
    private String usage;
    /** 使用禁忌 */
    private String contraindication;
    /** 产地 */
    private String production_area;
    /** 图片 URL */
    private String image_url;
    /** 浏览量 */
    private Integer views;
    /** 点赞数 */
    private Integer likes;
    /** 收藏数 */
    private Integer collections;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getHerb_id() {
        return herb_id;
    }

    public void setHerb_id(Integer herb_id) {
        this.herb_id = herb_id;
    }

    public String getHerb_name() {
        return herb_name;
    }

    public void setHerb_name(String herb_name) {
        this.herb_name = herb_name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    public String getProduction_area() {
        return production_area;
    }

    public void setProduction_area(String production_area) {
        this.production_area = production_area;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
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

    /** 状态（0 正常 1 停用） */
    private String status;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private Date create_time;



}
