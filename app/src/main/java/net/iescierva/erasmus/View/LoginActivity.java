// Author ==> Alberto Pérez Fructuoso
// File   ==> LoginActivity.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.gotev.uploadservice.UploadServiceConfig;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.Model.User;
import net.iescierva.erasmus.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


/**
 * Actividad inicial de la aplicacion para el inicio de sesión del usuario.
 */
public class LoginActivity extends AppCompatActivity {

    private TextView txtMessage;
    private EditText enterEmail;
    private EditText enterPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Se configura el servicio de subida de archivos de gotev.
        UploadServiceConfig.initialize(getApplication(), App.notificationChannelId, false);


        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);
        txtMessage = findViewById(R.id.txtMessage);
        progressBar = findViewById(R.id.indeterminateBar);

        Button btnLogin = findViewById(R.id.btnLogin);
        txtMessage.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(view -> login());

        // Evento de teclado para que al pulsar Enter en el teclado del movil, se dispare la función login(),
        // solo si esta enfocado en el campo de contraseña.
        enterPassword.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                login();
                return true;
            }
            return false;
        });
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);

        txtMessage.setVisibility(View.VISIBLE);
        txtMessage.setTextColor(Color.BLACK);
        txtMessage.setText(R.string.loading_session);

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = App.IP+"/api/login_on_android_app";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                this::createSession,
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    txtMessage.setTextColor(Color.RED);
                    txtMessage.setText(R.string.error_login);
                }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("name", "apiToken");
                params.put("email", String.valueOf(enterEmail.getText()));
                params.put("password", String.valueOf(enterPassword.getText()));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    /**
     * Crea una instancia de la clase User donde se
     * almacenará toda la información recogida desde la API
     * @param response El JSON devuelto por la API al iniciar la llamada "login_on_android_app"
     */
    private void createSession(String response)
    {
        progressBar.setVisibility(View.INVISIBLE);
        txtMessage.setVisibility(View.INVISIBLE);
        try {
            JSONObject jsonData = new JSONObject(response);
            App.user = new User(
                    jsonData.getString("AccessToken"),
                    jsonData.getString("Name"),
                    jsonData.getString("LastName"),
                    jsonData.getString("Email"),
                    jsonData.getString("DNI"),
                    jsonData.getJSONArray("Documents"),
                    jsonData.getString("CycleName"),
                    jsonData.getString("Date"),
                    jsonData.getString("Nationality"),
                    jsonData.getString("Locality"),
                    jsonData.getString("Phone"),
                    jsonData.getString("Address"),
                    jsonData.getString("ZIP")
            );
            System.out.println("==> OK :: Started User Session");
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}