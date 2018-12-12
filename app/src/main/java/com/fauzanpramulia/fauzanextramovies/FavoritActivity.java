package com.fauzanpramulia.fauzanextramovies;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.fauzanpramulia.fauzanextramovies.adapter.FavoritAdapter;
import com.fauzanpramulia.fauzanextramovies.adapter.MovieAdapter;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.CONTENT_URI;

public class FavoritActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    FavoritAdapter adapter;
    Cursor daftarFilm;
   // AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fauzan Favorit");
        toolbar.inflateMenu(R.menu.main_menu);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        new LoadNoteAsync().execute();

        adapter = new FavoritAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor daftar) {
            super.onPostExecute(daftar);
            progressBar.setVisibility(View.GONE);

            daftarFilm = daftar;
            adapter.setDataFilm(daftarFilm);
            adapter.notifyDataSetChanged();

            if (daftarFilm.getCount() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        new LoadNoteAsync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
        return super.onOptionsItemSelected(item);
    }
    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

}
