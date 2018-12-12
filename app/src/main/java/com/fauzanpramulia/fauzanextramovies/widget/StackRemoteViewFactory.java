package com.fauzanpramulia.fauzanextramovies.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.fauzanpramulia.fauzanextramovies.BuildConfig;
import com.fauzanpramulia.fauzanextramovies.R;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.support.constraint.Constraints.TAG;
import static com.fauzanpramulia.fauzanextramovies.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int mAppWidgetId;
    private Cursor cursor;

    public StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(position)) {
            return null;
        }

        MovieItems movie = getItem(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {

            String url = "http://image.tmdb.org/t/p/w300" + movie.getPoster_path();

            bmp = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Terjadi errorwidget", "error");
        }
        rv.setImageViewBitmap(R.id.imageWidget, bmp);
        return rv;
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    private MovieItems getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Posisi salah");
        }
        return new MovieItems(cursor);
    }
}
