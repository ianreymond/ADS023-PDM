package br.com.reymond.lawrence.oqrola.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.reymond.lawrence.oqrola.R;

/**
 * Created by ian on 03-Nov-16.
 */

public class EventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent it = new Intent(context, LoginActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, it, 0);

            Notification n = new Notification.Builder(context)
                    .setContentTitle("Boot Completo")
                    .setContentText("Boot Completo!")
                    .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();

            nm.notify(0, n);
        }
    }
}
