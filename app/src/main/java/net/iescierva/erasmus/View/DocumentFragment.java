package net.iescierva.erasmus.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;
import net.iescierva.erasmus.View.DocumentListAdapter;
import net.iescierva.erasmus.View.LoginActivity;
import org.json.JSONException;

import static android.app.Activity.RESULT_OK;

public class DocumentFragment extends Fragment {


    private RecyclerView recyclerView;

    private View homeView;

    public DocumentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeView = inflater.inflate(R.layout.fragment_document, container, false);
        try {
            Document[] documents = new Document[LoginActivity.user.getDocumentList().length()];

            for (int i = 0; i < LoginActivity.user.getDocumentList().length(); i++) {
                documents[i] = new Document(i, LoginActivity.user.getDocumentList().getJSONObject(i).getString("documento"));
            }
            DocumentListAdapter adapter = new DocumentListAdapter(documents);

            recyclerView = homeView.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            System.out.println("ERROR ==> "+e.getMessage());
        }
        return homeView;
    }
}