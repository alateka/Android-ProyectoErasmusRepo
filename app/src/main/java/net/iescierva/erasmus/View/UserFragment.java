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

        userName.setText(App.user.getName());
        userLastName.setText(App.user.getLastName());
        userEmail.setText(App.user.getEmail());
        userDNI.setText(App.user.getDNI());
        userCycle.setText(App.user.getCycleName());

        return homeView;
    }
}