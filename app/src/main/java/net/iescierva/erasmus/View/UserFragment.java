package net.iescierva.erasmus.View;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.View.LoginActivity;
import org.w3c.dom.Text;

public class UserFragment extends Fragment {

    private TextView userName;
    private TextView userLastName;
    private TextView userEmail;
    private TextView userDNI;
    private TextView userCycle;
    private TextView userBirthDate;
    private TextView userNationality;
    private TextView userLocality;
    private TextView userPhone;
    private TextView userAddress;
    private TextView userZIP;
    private View homeView;
    public UserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeView = inflater.inflate(R.layout.fragment_user, container, false);

        userName = homeView.findViewById(R.id.user_name);
        userLastName = homeView.findViewById(R.id.user_last_name);
        userEmail = homeView.findViewById(R.id.user_email);
        userDNI = homeView.findViewById(R.id.user_dni);
        userCycle = homeView.findViewById(R.id.user_cycle);
        userBirthDate = homeView.findViewById(R.id.user_birth_date);
        userNationality = homeView.findViewById(R.id.user_nationality);
        userLocality = homeView.findViewById(R.id.user_locality);
        userAddress = homeView.findViewById(R.id.user_address);
        userZIP = homeView.findViewById(R.id.user_zip_code);
        userPhone = homeView.findViewById(R.id.user_phone);


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