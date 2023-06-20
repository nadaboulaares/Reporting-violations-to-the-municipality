package com.example.bledi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Story {

    public ArrayList<Reclamation> getStory() {
        return story;
    }

    @SerializedName("story")
    ArrayList<Reclamation> story;
}
