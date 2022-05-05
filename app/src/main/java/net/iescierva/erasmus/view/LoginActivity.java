package net.iescierva.erasmus.view;

import android.content.Intent;
import net.iescierva.erasmus.Data.User;

// SDK Android
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.iescierva.erasmus.R;
import org.json.JSONException;
import org.json.JSONObject;

// JAVA API
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static User user;
    TextView txtMessage;
    EditText enterEmail;
    EditText enterPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterEmail = findViewById(R.id.enterEmail);
        enterPassword = findViewById(R.id.enterPassword);
        txtMessage = findViewById(R.id.txtMessage);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> login(enterEmail, enterPassword, txtMessage));

    }

    /*private void check()
    {
        try {
            String content = readApiFile(openFileInput("apiToken.dat"));
            System.out.println("Logueado");
            Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(i);
        } catch (IOException e) {
            login(enterEmail, enterPassword, txtMessage);
            System.out.println("Fichero creado");
        }
    }*/
    private void login(EditText enterEmail, EditText enterPassword, TextView txtMessage) {

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://192.168.7.111/api/loginonandroidapp";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> createSession(response),
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                    txtMessage.setText(R.string.errorLogin);
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
        try {
            JSONObject jsonData = new JSONObject(response);
            user = new User(jsonData.getString("AccessToken"), jsonData.getString("Name"), jsonData.getString("Email"));

            System.out.println("Logueado");
            Intent i = new Intent(this, MainMenuActivity.class);
            startActivity(i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void apiTokenToFile(String response, OutputStreamWriter apiFile)
    {
        try {
            apiFile.write(response);
            apiFile.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public static String readApiFile(InputStream in) throws IOException {
        String result = "";
        if(in != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = "";
            StringBuilder stringBuilder = new StringBuilder();

            while((temp = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }

            in.close();
            result = stringBuilder.toString();
        }
        return result;
    }*/
}