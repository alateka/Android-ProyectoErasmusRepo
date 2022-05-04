package net.iescierva.erasmus.view;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.iescierva.erasmus.R;

import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void prueba(View view) {
        RequestQueue queue = Volley.newRequestQueue(MainMenuActivity.this);
        String url = "http://192.168.7.111/api/getuserdata";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> System.out.println(response),
                error -> {
                    System.out.println(error.getMessage());
                }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization", "Bearer 11|bNzkG4LDu6u9hjXCQ1ZpvTEcG3ps8eBb16fNYGzN");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}