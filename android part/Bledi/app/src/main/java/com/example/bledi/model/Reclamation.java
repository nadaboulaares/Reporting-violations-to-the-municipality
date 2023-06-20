package com.example.bledi.model;

import com.google.gson.annotations.SerializedName;

public class Reclamation {


    public final String commentaires;
    public final String anomalie;
    public final String positionLatitude;
    public final String positionLongitude;

    public String getImageUrl() {
        return imageUrl;
    }

    @SerializedName("imageUrl")
    private String imageUrl;

    public final String date;
    public final String etat;
    public final String mEmail;


    public Reclamation(final String commentaires, String anAnomalie, String aPositionLatitude, String aPositionLongitude, String aDate, String aEtat, String amEmail) {
        this.commentaires = commentaires;

        anomalie = anAnomalie;
        positionLatitude = aPositionLatitude;
        positionLongitude = aPositionLongitude;

        date = aDate;
        etat = aEtat;
        mEmail = amEmail;

    }

    public void setCommentaires(String commentaires) {
        commentaires = commentaires;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public String getmEmail() {
        return mEmail;
    }


}






