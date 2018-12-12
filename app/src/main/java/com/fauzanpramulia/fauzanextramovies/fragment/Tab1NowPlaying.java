package com.fauzanpramulia.fauzanextramovies.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fauzanpramulia.fauzanextramovies.BuildConfig;
import com.fauzanpramulia.fauzanextramovies.MovieList;
import com.fauzanpramulia.fauzanextramovies.R;
import com.fauzanpramulia.fauzanextramovies.TmdbClient;
import com.fauzanpramulia.fauzanextramovies.adapter.MovieAdapter;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab1NowPlaying extends Fragment{
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<MovieItems> daftarFilm = new ArrayList<>();
    List<MovieItems> mItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_now_playing, container,false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        adapter = new MovieAdapter(getActivity());
        if (savedInstanceState != null )
        {
            mItems = savedInstanceState.getParcelableArrayList("movies");
            if (mItems != null){
                adapter.setDataFilm(new ArrayList<MovieItems>(mItems));
                daftarFilm =mItems;
            }

        }else{
            getNowPlayingMoview();
        }

        //adapter.setHandler();
        int orientation= getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void getNowPlayingMoview(){
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String API_BASE_URL = getResources().getString(R.string.url_base);
        String BAHASA_URL = getResources().getString(R.string.bahasa_film);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieList> call = client.getNomPlaying(BuildConfig.My_Db_api_key, BAHASA_URL);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response)   {
                MovieList movieList = response.body();
                List<MovieItems> listMovieItem = movieList.results;
                daftarFilm = listMovieItem;
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
