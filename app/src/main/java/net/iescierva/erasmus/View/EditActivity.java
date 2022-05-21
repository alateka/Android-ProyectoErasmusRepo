package net.iescierva.erasmus.View;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private OnMainMenuActivity onMainMenu;
    private EditText userName;
    private EditText userLastName;
    private EditText userEmail;
    private EditText userDNI;
    private ArrayList<String> spinnerItems;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        onMainMenu = new OnMainMenuActivity(this.getApplicationContext());

        userName = findViewById(R.id.edit_user_name);
        userLastName = findViewById(R.id.edit_user_last_name);
        userEmail = findViewById(R.id.edit_user_email);
        userDNI = findViewById(R.id.edit_user_dni);

        userName.setText(App.user.getName());
        userLastName.setText(App.user.getLastName());
        userEmail.setText(App.user.getEmail());
        userDNI.setText(App.user.getDNI());

        spinner = findViewById(R.id.spinner_fp);
        spinner.setOnItemSelectedListener(this);
        spinnerItems = new ArrayList();
        spinnerItems = onMainMenu.getCycles(spinnerItems);
        spinnerItems.add(App.user.getCycleName());
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}