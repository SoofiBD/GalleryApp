package com.example.galleryapp;

public class Photo {

    private String title;
    private String category;
    private String imageUrl;

    public Photo(String title, String category, String imageUrl) {
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

