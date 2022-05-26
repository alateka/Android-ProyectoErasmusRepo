package net.iescierva.erasmus.View;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;
import org.json.JSONException;

public class DocumentFragment extends Fragment {


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnMainMenuActivity onMainMenu;

    public DocumentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View homeView = inflater.inflate(R.layout.fragment_document, container, false);

        onMainMenu = new OnMainMenuActivity(this.getContext());

        try {
            Document[] documents = new Document[App.user.getDocumentList().length()];

            for (int i = 0; i < App.user.getDocumentList().length(); i++)
                documents[i] = new Document(App.user.getDocumentList().getJSONObject(i).getString("id"), App.user.getDocumentList().getJSONObject(i).getString("documento"));

            DocumentListAdapter adapter = new DocumentListAdapter(documents, onMainMenu);

            swipeRefreshLayout = homeView.findViewById(R.id.swipeRefreshLayout);
            recyclerView = homeView.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.setAdapter(adapter);
            swipeRefreshLayout.setOnRefreshListener(() -> {
                swipeRefreshLayout.setRefreshing(false);
                rearrangeItems();
            });
        } catch (JSONException e) {
            System.out.println("==> ERROR: "+e.getMessage());
        }
        return homeView;
    }

    public void rearrangeItems() {
        onMainMenu.reloadDocuments();
        Document[] documents = new Document[App.user.getDocumentList().length()];
        try {
            for (int i = 0; i < App.user.getDocumentList().length(); i++)
                documents[i] = new Document(App.user.getDocumentList().getJSONObject(i).getString("id"), App.user.getDocumentList().getJSONObject(i).getString("documento"));

            DocumentListAdapter adapter = new DocumentListAdapter(documents, onMainMenu);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            System.out.println("==> ERROR: "+e.getMessage());
        }
    }
}