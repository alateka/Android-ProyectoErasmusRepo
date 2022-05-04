package net.iescierva.erasmus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.iescierva.erasmus.view.MainMenuActivity;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView txtMessage;
    EditText enterEmail;
    EditText enterPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> check());

    }

    private void check()
    {
        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);
        txtMessage = findViewById(R.id.txtMessage);

        try {
            String content = readApiFile();
            System.out.println("Logueado");
            Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(i);
        } catch (IOException e) {
            login(enterEmail, enterPassword, txtMessage);
            System.out.println("Fichero creado");
        }
    }
    private void login(EditText enterEmail, EditText enterPassword, TextView txtMessage) {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://192.168.7.111/api/getoken";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> apiTokenToFile(response),
                error -> {
                    System.out.println(error.getMessage());
                    txtMessage.setText(error.getMessage());
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
    private void apiTokenToFile(String response)
    {
        try {
            OutputStreamWriter apiFile = new OutputStreamWriter(
                    openFileOutput("apiToken.dat", Context.MODE_PRIVATE)
            );
            apiFile.write(response);
            apiFile.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String readApiFile() throws IOException {
        InputStream inputStream = openFileInput("apiToken.dat");
        String result = "";
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = "";
            StringBuilder stringBuilder = new StringBuilder();

            while((temp = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }

            inputStream.close();
            result = stringBuilder.toString();
        }
        return result;
    }
}