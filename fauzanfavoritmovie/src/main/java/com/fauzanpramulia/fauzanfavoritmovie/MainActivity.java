package com.fauzanpramulia.fauzanfavoritmovie;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.fauzanpramulia.fauzanfavoritmovie.adapter.FavoritAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fauzanpramulia.fauzanfavoritmovie.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    FavoritAdapter adapter;
    Cursor daftarFilm;
    // AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
