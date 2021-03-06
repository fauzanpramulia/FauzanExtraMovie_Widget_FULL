package com.fauzanpramulia.fauzanextramovies.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.BaseColumns._ID;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.ID;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.TITLE;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.getColumnInt;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    public int id;
    public String title;
    public String overview;
    public double vote_average;
    public String release_date;
    public String poster_path;

    public MovieItems() {
    }

    public MovieItems(int id, String title, String overview, double vote_average, String release_date, String poster_path) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
    }

    public MovieItems(Cursor cursor){
        this.id = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.vote_average = getColumnInt(cursor, VOTE_AVERAGE);
        this.release_date = getColumnString(cursor, RELEASE_DATE);
        this.poster_path = getColumnString(cursor, POSTER_PATH);
    }
    public MovieItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
        this.release_date = in.readString();
        this.poster_path = in.readString();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
