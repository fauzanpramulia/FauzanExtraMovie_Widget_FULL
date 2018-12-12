package com.fauzanpramulia.fauzanextramovies;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.fauzanpramulia.fauzanextramovies.model.MovieItems;
import com.fauzanpramulia.fauzanextramovies.reminder.DailyAlarmReceiver;
import com.fauzanpramulia.fauzanextramovies.reminder.UpcomingAlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.alarmDaily) Switch swDaily;
    @BindView(R.id.alarmMovie) Switch swMovie;
    @BindView(R.id.simpan) Button simpan;
    List<MovieItems> daftarFilm = new ArrayList<>();
    UpcomingAlarmReceiver upPlayingAlarm;
    String currentDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Setting Alarm");
        final DailyAlarmReceiver dailyAlarmReceiver = new DailyAlarmReceiver();
        upPlayingAlarm = new UpcomingAlarmReceiver();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
         currentDate = dateFormat.format(date);
        Log.e("currentDate", currentDate);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (swDaily.isChecked()){
                    swDaily.setText("On");
                    dailyAlarmReceiver.setRepeatingAlarm(getApplicationContext());
                }
                else{
                    swDaily.setText("Off");
                    dailyAlarmReceiver.cancelAlarm(getApplicationContext());
                }

                if (swMovie.isChecked()){
                    swMovie.setText("On");
                    alarmUpPlayingSetting();
                }
                else{
                    cancelAlarm();
                    swMovie.setText("Off");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id==R.id.menu_refresh){
//            //getNowPlayingMoview();
//            //loadDummyData();
//        }
        if (id==R.id.menu_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        if (id==R.id.favorit){
            Intent intent = new Intent(this, FavoritActivity.class);
            startActivity(intent);
        }
        if (id==R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id==R.id.settings){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void alarmUpPlayingSetting(){

        String API_BASE_URL = getResources().getString(R.string.url_base);
        String BAHASA_URL = getResources().getString(R.string.bahasa_film);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getUpcoming(BuildConfig.My_Db_api_key, BAHASA_URL);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response)   {
                MovieList movieList = response.body();
                List<MovieItems> listMovieItem = movieList.results;
                for (MovieItems movie : listMovieItem) {
                    if (movie.getRelease_date().equals(currentDate)) {
                        setAlarm(movie);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public void setAlarm(MovieItems movie) {
        this.daftarFilm.clear();
        this.daftarFilm.add(movie);
        upPlayingAlarm.setRepeatingAlarm(this, daftarFilm);
    }
    public void cancelAlarm() {
        upPlayingAlarm.cancelAlarm(this);
    }
}
