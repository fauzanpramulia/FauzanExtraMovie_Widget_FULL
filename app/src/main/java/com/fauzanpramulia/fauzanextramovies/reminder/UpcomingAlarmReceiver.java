package com.fauzanpramulia.fauzanextramovies.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fauzanpramulia.fauzanextramovies.DetailActivity;
import com.fauzanpramulia.fauzanextramovies.R;
import com.fauzanpramulia.fauzanextramovies.model.MovieItems;

import java.util.Calendar;
import java.util.List;

public class UpcomingAlarmReceiver extends BroadcastReceiver {
    private static int notifId = 1000;
    public UpcomingAlarmReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int notifId = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        Log.e("id", String.valueOf(notifId));
        showAlarmNotification(context, title, notifId);
    }
    private void showAlarmNotification(Context context, String title, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(String.valueOf(String.format(context.getString(R.string.upcoming_reminder_msg), title)))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmRingtone);

        notificationManager.notify(notifId, builder.build());
    }

    public void setRepeatingAlarm(Context context, List<MovieItems> movies) {
        int delay = 0;
        for (MovieItems movie : movies) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, UpcomingAlarmReceiver.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("id", notifId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );

            notifId += 1;
            delay += 5000;
            Log.e("title", movie.getTitle());
        }
    }
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent alarmIntent = new Intent(context, UpcomingAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 101, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
