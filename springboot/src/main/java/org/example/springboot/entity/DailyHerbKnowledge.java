package org.example.springboot.entity;

import java.util.Date;

public class DailyHerbKnowledge {

    private Integer id;
    private String herb_name;
    private String knowledge_content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHerb_name() {
        return herb_name;
    }

    public void setHerb_name(String herb_name) {
        this.herb_name = herb_name;
    }

    public String getKnowledge_content() {
        return knowledge_content;
    }

    public void setKnowledge_content(String knowledge_content) {
        this.knowledge_content = knowledge_content;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    private Date publish_date;

}
