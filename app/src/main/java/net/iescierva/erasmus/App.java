// Author ==> Alberto Pérez Fructuoso
// File   ==> App.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus;


import android.app.Application;
import net.gotev.uploadservice.UploadService;
import net.iescierva.erasmus.Model.User;

public class App extends Application {
    public static final String IP = "http://erasmus.es";
    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();

        // Linea necesaría para el funcionamiento de algunas notificaciónes
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;

        System.out.println("Started Application");
    }
}