// Author ==> Alberto Pérez Fructuoso
// File   ==> EditActivity.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.Actions;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Actions activity;
    private EditText userName;
    private EditText userLastName;
    private EditText userEmail;
    private EditText userDNI;
    private TextView userBirthDate;
    private TextView userNationality;
    private TextView userLocality;
    private TextView userPhone;
    private TextView userAddress;
    private TextView userZIP;
    private String cycleSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        activity = new Actions(this.getApplicationContext());

        userName = findViewById(R.id.edit_user_name);
        userLastName = findViewById(R.id.edit_user_last_name);
        userEmail = findViewById(R.id.edit_user_email);
        userDNI = findViewById(R.id.edit_user_dni);
        userBirthDate = findViewById(R.id.edit_user_birth_date);
        userNationality = findViewById(R.id.edit_user_nationality);
        userLocality = findViewById(R.id.edit_user_locality);
        userAddress = findViewById(R.id.edit_user_address);
        userZIP = findViewById(R.id.edit_user_zip_code);
        userPhone = findViewById(R.id.edit_user_phone);

        userName.setText(App.user.getName());
        userLastName.setText(App.user.getLastName());
        userEmail.setText(App.user.getEmail());
        userDNI.setText(App.user.getDNI());
        userBirthDate.setText(App.user.getBirthDate());
        userNationality.setText(App.user.getNationality());
        userLocality.setText(App.user.getLocality());
        userAddress.setText(App.user.getAddress());
        userZIP.setText(App.user.getZip());
        userPhone.setText(App.user.getPhone());

        Spinner spinner = findViewById(R.id.spinner_fp);
        spinner.setOnItemSelectedListener(this);
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems = activity.getCycles(spinnerItems);
        spinnerItems.add(App.user.getCycleName());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cycleSelected = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.user_save_action) {
            activity.updateUser(
                    userName.getText().toString(),
                    userDNI.getText().toString(),
                    userLastName.getText().toString(),
                    userEmail.getText().toString(),
                    cycleSelected,
                    userBirthDate.getText().toString(),
                    userNationality.getText().toString(),
                    userLocality.getText().toString(),
                    userPhone.getText().toString(),
                    userAddress.getText().toString(),
                    userZIP.getText().toString()
            );
            App.user.setName(userName.getText().toString());
            App.user.setDNI(userDNI.getText().toString());
            App.user.setLastName(userLastName.getText().toString());
            App.user.setEmail(userEmail.getText().toString());
            App.user.setCycleName(cycleSelected);
            App.user.setBirthDate(userBirthDate.getText().toString());
            App.user.setNationality(userNationality.getText().toString());
            App.user.setLocality(userLocality.getText().toString());
            App.user.setPhone(userPhone.getText().toString());
            App.user.setAddress(userAddress.getText().toString());
            App.user.setZip(userZIP.getText().toString());
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }
}