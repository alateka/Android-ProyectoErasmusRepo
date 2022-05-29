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

        // Notificaciónes de subida de archivos (gotev - uploadservice)
        // Es necesarío declarar el espacio de nombres en la constante "NAMESPACE" de la clase "UploadService"
        // que en este caso es "net.iescierva.erasmus"
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;

        System.out.println("Started Application");
    }
}