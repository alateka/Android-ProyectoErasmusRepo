package net.iescierva.erasmus;


import android.app.*;
import android.os.Build;

public class App extends Application {
    public static final String IP = "http://erasmus.es";
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Started Application");
    }
}