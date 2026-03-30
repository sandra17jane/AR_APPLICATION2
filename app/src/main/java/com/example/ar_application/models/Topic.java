package com.example.ar_application.models;

public class Topic {
    private String component;
    private String content;
    private String imageUrl;

    // Constructor
    public Topic(String component, String content, String imageUrl) {
        this.component = component;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // ✅ Getter methods (Fixes the error)
    public String getComponent() {
        return component;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
