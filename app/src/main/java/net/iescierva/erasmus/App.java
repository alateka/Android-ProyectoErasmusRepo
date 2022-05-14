package net.iescierva.erasmus;


import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("Started Application");
    }
}