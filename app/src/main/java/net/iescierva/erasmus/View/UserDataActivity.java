package net.iescierva.erasmus.View;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.switch_document_action) {
            Intent i = new Intent(this, UserDocumentsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }
}