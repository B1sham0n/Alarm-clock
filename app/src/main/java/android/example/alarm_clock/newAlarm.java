package android.example.alarm_clock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.Format;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;


public class newAlarm extends BroadcastReceiver {
    final public static String ONE_TIME="onetime";
    private static final String CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, newAlarm.class);
        System.out.println(System.currentTimeMillis()/1000/60);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//стандартная мелодия будильника
        //---для новых версий андроида нужен канал
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            //настройки канала
            channel.setDescription("To turn off click on the notification!");
            channel.enableLights(true);
            channel.setLightColor(Color.YELLOW);
            channel.enableVibration(true);
            channel.setSound(alarmTone, null);
            //показать уведомление
            notificationManager.createNotificationChannel(channel);
        }
        //---для старых версий андроида
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm Clock")
                .setContentText("To turn off click the notification!") // Текст уведомления
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE), AudioManager.STREAM_RING)//2 параметр для бесконечного рингтона
                .setContentIntent(contentIntent)
                .setAutoCancel(true)//при нажатии закрыть уведомление + должен отключиться звук
                .setColor(Color.YELLOW);
        //.setChannelId("1");//ранее был создан канал с CHANNEL_ID=1

        //показываю уведомление
        notificationManager.notify(1, builder.build());
        System.out.println("waaaaaaaaaaaake up");
    }

}