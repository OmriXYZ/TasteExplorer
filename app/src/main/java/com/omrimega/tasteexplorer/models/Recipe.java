package com.omrimega.tasteexplorer.models;

import java.util.List;

public class Recipe {
    private String title;
    private String tags;
    private String instructions;
    private String numOfPersons;
    private String time;
    private String creator;
    private String image;

    public Recipe(String title, String tags, String instructions, String numOfPersons, String creator, String time, String image) {
        this.title = title;
        this.tags = tags;
        this.instructions = instructions;
        this.numOfPersons = numOfPersons;
        this.creator = creator;
        this.time = time;
        this.image = image;
    }

    public Recipe() {

    }
    // Getters and setters for the attributes


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getNumOfPersons() {
        return numOfPersons;
    }

    public void setNumOfPersons(String numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
