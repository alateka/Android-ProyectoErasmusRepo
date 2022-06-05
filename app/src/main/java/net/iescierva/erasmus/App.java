// Author ==> Alberto Pérez Fructuoso
// File   ==> App.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import net.iescierva.erasmus.Model.User;

public class App extends Application {
    // Constante usada para indicar el host donde se esta alogando la web
    // para poder hacer uso de la API.
    public static final String IP = "http://erasmus.albertico.tk:83";
    public static User user;

    public static final String notificationChannelId = "erasmus_repo";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        System.out.println("Started Application");
    }

    /**
     * Crea el sistema de notificaciones para la APP.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(notificationChannelId, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}