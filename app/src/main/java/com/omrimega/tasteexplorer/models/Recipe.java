package com.omrimega.tasteexplorer.models;

import java.util.List;

public class Recipe {
    private String title;
    private List<String> tags;
    private String difficulty;
    private int numOfPersons;
    private String creator;
    private int time;
    private List<String> photos;
    private String description;

    public Recipe(String title, List<String> tags, String difficulty, int numOfPersons, String creator, int time, List<String> photos, String description) {
        this.title = title;
        this.tags = tags;
        this.difficulty = difficulty;
        this.numOfPersons = numOfPersons;
        this.creator = creator;
        this.time = time;
        this.photos = photos;
        this.description = description;
    }

    // Getters and setters for the attributes

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumOfPersons() {
        return numOfPersons;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
