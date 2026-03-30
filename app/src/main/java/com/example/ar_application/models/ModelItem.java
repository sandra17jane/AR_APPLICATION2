package com.example.ar_application.models;

public class ModelItem {
    private String modelName;
    private String modelPath;
    private int thumbnailResId;
    public ModelItem(String modelName, String modelPath, int thumbnailResId) {
        this.modelName = modelName;
        this.modelPath = modelPath;
        this.thumbnailResId = thumbnailResId;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelPath() {
        return modelPath;
    }

    public int getThumbnailResId() {
        return thumbnailResId;
    }
}
