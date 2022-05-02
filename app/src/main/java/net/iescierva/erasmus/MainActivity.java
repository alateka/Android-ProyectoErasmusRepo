package net.iescierva.erasmus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button boton = findViewById(R.id.buttonAPI);
        //boton.setOnClickListener(view -> get());
    }

    private void get(TextView texto) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://192.168.7.253/api/apitest";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> texto.setText(response), error -> {
            texto.setText(error.getMessage());
            System.out.println(error.getMessage());
        }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer 1|krtG40reaabLqWxcAqnE4kBloxQBUBeZpBN7E208");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void post(TextView texto) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://cat-fact.herokuapp.com/facts/random";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                texto.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                texto.setText("Fallo");
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Clave", "Valor");
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer 6|lmOZne3SWMqq8IyXnr17DGiMngRSNF5r0gQaVVfh");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}