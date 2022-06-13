// Author ==> Alberto Pérez Fructuoso
// File   ==> AboutActivity.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        VideoView videoErasmus = findViewById(R.id.video_erasmus);
        videoErasmus.setVideoPath(App.IP+"/video/MasterFinal-erasmus.webm");
        videoErasmus.setMediaController(new MediaController(AboutActivity.this));
        videoErasmus.start();
        videoErasmus.requestFocus();
    }

    /**
     * Este método crea el menu de opciones en la barra de
     * herramientas en la vista de acerca sobre la APP.
     * @param menu El menu sobre el que añadir las opciones.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    /**
     * Este método se encarga de ejecutar la función de la opción del menu que fue seleccionada.
     * @param item La opción del menu que fue seleccionada.
     */
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back_action) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}