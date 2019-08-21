package com.example.moviecatalogue.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;


public class MoviesTv implements Parcelable {

    // for Movie Name
    @SerializedName("title")
    private String name;

    @SerializedName("vote_average")
    private float rating;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String overview;

    // for tv name
    @SerializedName("name")
    private String TvName;

    private int ChooseTarget;

    public int getChooseTarget() {
        return ChooseTarget;
    }

    public void setChooseTarget(int chooseTarget) {
        ChooseTarget = chooseTarget;
    }

    public String getTvName() {
        return TvName;
    }

    public void setTvName(String tvName) {
        TvName = tvName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.rating);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
        dest.writeString(this.TvName);
        dest.writeInt(this.ChooseTarget);
    }

    public MoviesTv() {
    }

    protected MoviesTv(Parcel in) {
        this.name = in.readString();
        this.rating = in.readFloat();
        this.poster = in.readString();
        this.overview = in.readString();
        this.TvName = in.readString();
        this.ChooseTarget = in.readInt();
    }

    public static final Creator<MoviesTv> CREATOR = new Creator<MoviesTv>() {
        @Override
        public MoviesTv createFromParcel(Parcel source) {
            return new MoviesTv(source);
        }

        @Override
        public MoviesTv[] newArray(int size) {
            return new MoviesTv[size];
        }
    };
}
