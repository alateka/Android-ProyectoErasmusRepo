package net.iescierva.erasmus;


import android.app.*;
import android.os.Build;
import net.iescierva.erasmus.Model.User;

public class App extends Application {
    public static final String IP = "http://erasmus.es";
    public static User user;
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Started Application");
    }
}