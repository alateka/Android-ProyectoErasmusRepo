package net.iescierva.erasmus.View;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.View.LoginActivity;

public class UserFragment extends Fragment {

    private TextView userName;
    private TextView userLastName;
    private TextView userEmail;
    private TextView userDNI;
    private View homeView;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*userName = homeView.findViewById(R.id.user_name);
        userLastName = homeView.findViewById(R.id.user_last_name);
        userEmail = homeView.findViewById(R.id.user_email);
        userDNI = homeView.findViewById(R.id.user_dni);

        userName.setText(LoginActivity.user.getName());
        userLastName.setText(LoginActivity.user.getLastName());
        userEmail.setText(LoginActivity.user.getEmail());
        userDNI.setText(LoginActivity.user.getDNI());*/

        homeView = inflater.inflate(R.layout.fragment_user, container, false);
        return homeView;
    }
}