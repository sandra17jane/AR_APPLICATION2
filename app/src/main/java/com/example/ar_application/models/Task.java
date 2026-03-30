package com.example.ar_application.models;

public class Task {
    private String title;
    private String description;
    private String imageUrl; // ✅ Add Image URL Field

    // Default constructor (required for Firebase)
    public Task() {}

    // Constructor
    public Task(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl; // ✅ Set Image URL
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() { // ✅ New Getter
        return imageUrl;
    }
}
