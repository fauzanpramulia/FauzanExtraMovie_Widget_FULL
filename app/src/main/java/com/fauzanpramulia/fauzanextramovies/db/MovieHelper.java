package com.fauzanpramulia.fauzanextramovies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;

import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.ID;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.TITLE;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.TABLE_NAME;

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    public ArrayList<MovieItems> query() {
        ArrayList<MovieItems> arrayList = new ArrayList<MovieItems>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, ID + " DESC", null);
        cursor.moveToFirst();
        MovieItems movie;
        if (cursor.getCount() > 0) {
            do {

                movie = new MovieItems();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public long insert(MovieItems movie) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(ID, movie.getId());
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(VOTE_AVERAGE, movie.getVote_average());
        initialValues.put(RELEASE_DATE, movie.getRelease_date());
        initialValues.put(POSTER_PATH, movie.getPoster_path());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }


    public int update(MovieItems movie) {
        ContentValues args = new ContentValues();

        args.put(ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(VOTE_AVERAGE, movie.getVote_average());
        args.put(RELEASE_DATE, movie.getRelease_date());
        args.put(POSTER_PATH, movie.getPoster_path());
        return database.update(DATABASE_TABLE, args, ID + "= '" + movie.getId() + "'", null);
    }


    public int delete(int id) {
        return database.delete(TABLE_NAME, ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }
}
