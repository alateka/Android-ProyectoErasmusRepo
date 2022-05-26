package net.iescierva.erasmus.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;

public class UserFragment extends Fragment {

    public UserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View homeView = inflater.inflate(R.layout.fragment_user, container, false);

        TextView userName = homeView.findViewById(R.id.user_name);
        TextView userLastName = homeView.findViewById(R.id.user_last_name);
        TextView userEmail = homeView.findViewById(R.id.user_email);
        TextView userDNI = homeView.findViewById(R.id.user_dni);
        TextView userCycle = homeView.findViewById(R.id.user_cycle);
        TextView userBirthDate = homeView.findViewById(R.id.user_birth_date);
        TextView userNationality = homeView.findViewById(R.id.user_nationality);
        TextView userLocality = homeView.findViewById(R.id.user_locality);
        TextView userAddress = homeView.findViewById(R.id.user_address);
        TextView userZIP = homeView.findViewById(R.id.user_zip_code);
        TextView userPhone = homeView.findViewById(R.id.user_phone);


        userName.setText(App.user.getName());
        userLastName.setText(App.user.getLastName());
        userEmail.setText(App.user.getEmail());
        userDNI.setText(App.user.getDNI());
        userCycle.setText(App.user.getCycleName());
        userBirthDate.setText(App.user.getBirthDate());
        userNationality.setText(App.user.getNationality());
        userLocality.setText(App.user.getLocality());
        userAddress.setText(App.user.getAddress());
        userZIP.setText(App.user.getZip());
        userPhone.setText(App.user.getPhone());

        return homeView;
    }
}