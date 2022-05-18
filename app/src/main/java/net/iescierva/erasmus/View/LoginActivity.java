package net.iescierva.erasmus.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ProgressBar;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.Model.User;

// SDK Android
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import net.iescierva.erasmus.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    public static User user;
    private TextView txtMessage;
    private EditText enterEmail;
    private EditText enterPassword;
    private Button btnLogin;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);
        txtMessage = findViewById(R.id.txtMessage);
        progressBar = findViewById(R.id.indeterminateBar);

        btnLogin = findViewById(R.id.btnLogin);
        txtMessage.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(view -> login());
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);

        txtMessage.setVisibility(View.VISIBLE);
        txtMessage.setTextColor(Color.BLACK);
        txtMessage.setText(R.string.loading_session);

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = App.IP+"/api/loginonandroidapp";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> createSession(response),
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

    private void createSession(String response)
    {
        progressBar.setVisibility(View.INVISIBLE);
        txtMessage.setVisibility(View.INVISIBLE);
        try {
            JSONObject jsonData = new JSONObject(response);
            user = new User(
                    jsonData.getString("AccessToken"),
                    jsonData.getString("Name"),
                    jsonData.getString("LastName"),
                    jsonData.getString("Email"),
                    jsonData.getString("DNI"),
                    jsonData.getJSONArray("Documents")
            );

            System.out.println("==> OK :: Started User Session");
            Intent i = new Intent(this, Home.class);
            startActivity(i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}