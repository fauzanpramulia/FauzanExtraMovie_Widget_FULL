package com.fauzanpramulia.fauzanextramovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbClient {
    @GET("/3/movie/now_playing")
    Call<MovieList> getNomPlaying(@Query("api_key") String api_key, @Query("language") String bahasa);

    @GET("/3/movie/upcoming")
    Call<MovieList> getUpcoming(@Query("api_key") String api_key, @Query("language") String bahasa);

    @GET("/3/search/movie")
    Call<MovieList> getCariMovie(@Query("api_key") String api_key
            ,@Query("language") String bahasa
            ,@Query("query") String nama);

}
