package com.cookandroid.gocafestudy.models;

import java.util.Date;

public class Feature {
    private int featureId;
    private String groupName;
    private String name;
    private String valueType;
    private String description;
    private Date createdAt;

    public Feature(int featureId, String groupName, String name, String valueType, String description, Date createdAt) {
        this.featureId = featureId;
        this.groupName = groupName;
        this.name = name;
        this.valueType = valueType;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
// Getter, Setter 생략
}
