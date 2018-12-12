package com.fauzanpramulia.fauzanfavoritmovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.fauzanpramulia.fauzanfavoritmovie.model.MovieItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.CONTENT_URI;
import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.MovieColumns.ID;
import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_title)TextView detailTitle;
    @BindView(R.id.detail_overview)TextView detailDesk;
    @BindView(R.id.detail_rating)TextView detailRating;
    @BindView(R.id.detail_TanggalRilis)TextView detailTanggal;
    @BindView(R.id.detail_poster) ImageView imgView;
    @BindView(R.id.myToggleButton) ToggleButton toggleButton;
    public static String EXTRA_DETAIL_MOVIE = "extra_detail_movie";
    Cursor check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final MovieItems movie = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);

        detailTitle.setText(movie.getTitle());
        detailDesk.setText(movie.getOverview());
        detailRating.setText(String.valueOf(movie.getVote_average()));
        detailTanggal.setText(movie.getRelease_date());

        String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
        Glide.with(this)
                .load(url)
                .into(imgView);


        int ada = checkData(movie.getId());
        if (ada==1){
            toggleButton.setChecked(true);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.img_star_yellow));
        }else{
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
        }toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ContentValues initialValues = new ContentValues();

                    initialValues.put(POSTER_PATH, movie.getPoster_path());
                    initialValues.put(RELEASE_DATE, movie.getRelease_date());
                    initialValues.put(VOTE_AVERAGE, movie.getVote_average());
                    initialValues.put(OVERVIEW, movie.getOverview());
                    initialValues.put(TITLE, movie.getTitle());
                    initialValues.put(ID, movie.getId());
                    getContentResolver().insert(CONTENT_URI,initialValues);
                    Toast.makeText(DetailActivity.this, "Film "+movie.getTitle()+" Menjadi Favorit", Toast.LENGTH_SHORT).show();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.img_star_yellow));
                }
                else{
                    getContentResolver().delete(getIntent().getData(),null,null);
                    Toast.makeText(DetailActivity.this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_star_grey));
                }

            }
        });
    }

    public int checkData(int id){
        Uri uri = getIntent().getData();
        if(uri != null){
            check = getContentResolver().query(uri,null,null,null,null);
            if (check.getCount() == 0){
                return 0;
            }else{
                return 1;
            }
        }else{
            return 0;
        }

    }
}
