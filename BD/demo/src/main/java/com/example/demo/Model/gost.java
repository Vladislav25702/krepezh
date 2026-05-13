package com.example.demo.Model;

import java.util.List;
import java.util.Map;

public class gost {
    private Integer id;
    private String title;
    private String type;
    private String gostNumber;
    private List<Map<String, Object>> properties;

    public gost() {}

    public gost(Integer id, String title, String type, String gostNumber, List<Map<String, Object>> properties) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.gostNumber = gostNumber;
        this.properties = properties;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getGostNumber() { return gostNumber; }
    public void setGostNumber(String gostNumber) { this.gostNumber = gostNumber; }

    public List<Map<String, Object>> getProperties() { return properties; }
    public void setProperties(List<Map<String, Object>> properties) { this.properties = properties; }
}