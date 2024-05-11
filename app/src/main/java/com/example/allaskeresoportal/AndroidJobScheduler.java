package com.example.allaskeresoportal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AndroidJobScheduler {
    private static final String ID = "android_job_channel";

    private NotificationManager notificationManager;
    private Context context;

    public AndroidJobScheduler(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        create();
    }

    private void create() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(
            ID,
            "Andorid job notification",
            NotificationManager.IMPORTANCE_DEFAULT
        );

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.BLUE);
        channel.setDescription("Értesítés");
        this.notificationManager.createNotificationChannel(channel);
    }

    public void send(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ID)
                .setContentTitle("Andorid Job")
                .setContentText("Sikeres hozzáadás: " + message)
                .setSmallIcon(R.drawable.ic_accessibility);

        this.notificationManager.notify(0, builder.build());
    }
}
