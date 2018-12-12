package com.fauzanpramulia.fauzanextramovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fauzanpramulia.fauzanextramovies.DetailActivity;
import com.fauzanpramulia.fauzanextramovies.R;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;

import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.CONTENT_URI;

public class FavoritAdapter extends  RecyclerView.Adapter<FavoritAdapter.MovieHolder> {
    Cursor dataFilm;
    Context context;
    public void setDataFilm(Cursor films) {
        this.dataFilm = films;
        notifyDataSetChanged();
    }

    public FavoritAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FavoritAdapter.MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritAdapter.MovieHolder holder, final int position) {
        final MovieItems movie;
        if (getItem(position).getOverview() != null) {
            {
                movie = getItem(position);
                holder.textTitle.setText(movie.getTitle());
                String potongDesk = truncate(getItem(position).getOverview(), 45);
                holder.textDesk.setText(potongDesk);
                holder.textTanggalRilis.setText(movie.getRelease_date());
                holder.textRating.setText(String.valueOf(movie.getVote_average()));
                String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();
                Glide.with(holder.itemView)
                        .load(url)
                        .into(holder.imagePoster);
            }

            holder.view_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // sending data process
                    Intent i = new Intent(context, DetailActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI+"/"+ getItem(position).getId());
                    i.setData(uri);
                    i.putExtra(DetailActivity.EXTRA_DETAIL_MOVIE, getItem(position));
                    context.startActivity(i);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (dataFilm == null) return 0;
        return dataFilm.getCount();
    }
    private MovieItems getItem(int position){
        if (!dataFilm.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItems(dataFilm);
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView textTitle;
        TextView textDesk;
        TextView textTanggalRilis;
        TextView textRating;
        CardView view_container;
        public MovieHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.img_poster);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesk = itemView.findViewById(R.id.textDesk);
            textTanggalRilis = itemView.findViewById(R.id.textTanggalRilis);
            textRating = itemView.findViewById(R.id.textRating);
            view_container = (CardView)itemView.findViewById(R.id.container);
        }
    }

    public static String truncate(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len) + "...";
        } else {
            return str;
        }
    }

}
