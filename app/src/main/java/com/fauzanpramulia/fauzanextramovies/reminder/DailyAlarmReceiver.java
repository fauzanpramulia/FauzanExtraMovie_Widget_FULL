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
import android.widget.Toast;

import com.fauzanpramulia.fauzanextramovies.MainActivity;
import com.fauzanpramulia.fauzanextramovies.R;

import java.util.Calendar;
import java.util.Date;

public class DailyAlarmReceiver extends BroadcastReceiver{
    private static final int NOTIFICATION_ID = 101;
    public DailyAlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String appName = context.getString(R.string.app_name);
        String message = context.getString(R.string.msg_daily_reminder);
        showAlarmNotification(context, appName, message, NOTIFICATION_ID);
    }

    public void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmRingtone);

        notificationManager.notify(notifId, builder.build());
    }

    public void setRepeatingAlarm(Context context) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    getPendingIntent(context)
            );
        Toast.makeText(context, "Alarm Daily Dialog ON", Toast.LENGTH_SHORT).show();

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, "Alarm Daily Catalog OFF", Toast.LENGTH_SHORT).show();
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent alarmIntent = new Intent(context, DailyAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
