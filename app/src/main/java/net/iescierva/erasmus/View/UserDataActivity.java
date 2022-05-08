package net.iescierva.erasmus.View;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import net.iescierva.erasmus.R;

public class UserDataActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userLastName;
    private TextView userEmail;
    private TextView userDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        userName = findViewById(R.id.user_name);
        userLastName = findViewById(R.id.user_last_name);
        userEmail = findViewById(R.id.user_email);
        userDNI = findViewById(R.id.user_dni);

        userName.setText(LoginActivity.user.getName());
        userLastName.setText(LoginActivity.user.getLastName());
        userEmail.setText(LoginActivity.user.getEmail());
        userDNI.setText(LoginActivity.user.getDNI());
    }
}