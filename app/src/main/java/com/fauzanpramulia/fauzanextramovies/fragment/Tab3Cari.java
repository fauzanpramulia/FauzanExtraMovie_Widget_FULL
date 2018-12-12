package com.fauzanpramulia.fauzanextramovies.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fauzanpramulia.fauzanextramovies.BuildConfig;
import com.fauzanpramulia.fauzanextramovies.DetailActivity;
import com.fauzanpramulia.fauzanextramovies.MovieList;
import com.fauzanpramulia.fauzanextramovies.R;
import com.fauzanpramulia.fauzanextramovies.TmdbClient;
import com.fauzanpramulia.fauzanextramovies.adapter.MovieAdapter;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class Tab3Cari extends Fragment {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    TextView namaMovie;
    Button cari;
    List<MovieItems> daftarFilm = new ArrayList<>();
    List<MovieItems> mItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_cari_movie, container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        namaMovie = (TextView)view.findViewById(R.id.edit_movie) ;
        adapter = new MovieAdapter(getActivity());

        if (savedInstanceState != null)
        {
            mItems = savedInstanceState.getParcelableArrayList("movies");
            adapter.setDataFilm(new ArrayList<MovieItems>(mItems));
            daftarFilm =mItems;
        }

        cari = (Button)view.findViewById(R.id.btn_cari) ;
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCariMovie(namaMovie.getText().toString());
            }
        });

        int orientation= getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void getCariMovie(String name){
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String API_BASE_URL = getResources().getString(R.string.url_base);
        String BAHASA_URL = getResources().getString(R.string.bahasa_film);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getCariMovie(BuildConfig.My_Db_api_key, BAHASA_URL, name);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response)   {
                MovieList movieList = response.body();
                List<MovieItems> listMovieItem = movieList.results;
                daftarFilm =listMovieItem;
                adapter.setDataFilm(new ArrayList<MovieItems>(listMovieItem));
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) daftarFilm);
    }
}
