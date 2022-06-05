// Author ==> Alberto Pérez Fructuoso
// File   ==> DocumentFragment.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.Actions;
import org.json.JSONException;

/**
 * Clase encargada de representar el contenido de la pestaña de documentos en la APP.
 */
public class DocumentFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Actions actions;

    private Document[] documents;

    public DocumentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_document, container, false);


        actions = new Actions(this.getContext());

        try {
            documents = new Document[App.user.getDocumentList().length()];
        // Se obtienen todos los documentos del usuario (Clase User) y se añaden a la matríz llamada "documents"
            for (int i = 0; i < App.user.getDocumentList().length(); i++)
                documents[i] = new Document(App.user.getDocumentList().getJSONObject(i).getString("id"),
                        App.user.getDocumentList().getJSONObject(i).getString("documento"));


            // Se instancia el adaptador
            DocumentListAdapter adapter = new DocumentListAdapter(documents, actions);

            // Se establecen y se configuran los objetos de la vista llamada "fragment_document.xml"
            swipeRefreshLayout = homeView.findViewById(R.id.swipeRefreshLayout);
            recyclerView = homeView.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.setAdapter(adapter);

            // Cada vez que el usuario extienda el dedo hacia abajo, se refrescarán los documentos
            swipeRefreshLayout.setOnRefreshListener(() -> {
                swipeRefreshLayout.setRefreshing(false);
                actions.reloadDocuments();
                rearrangeItems();
            });

        } catch (JSONException e) {
            System.out.println("==> ERROR: "+e.getMessage());
        }
        return homeView;
    }

    /**
     * Este método regenera el listado de documentos del usuario (Clase User) usando la API
     * para obtener datos actiualizados sobre los documentos actualmente subidos.
     */
    public void rearrangeItems() {
        documents = new Document[App.user.getDocumentList().length()];
        try {
            for (int i = 0; i < App.user.getDocumentList().length(); i++)
                documents[i] = new Document(App.user.getDocumentList().getJSONObject(i).getString("id"),
                        App.user.getDocumentList().getJSONObject(i).getString("documento"));

            DocumentListAdapter adapter = new DocumentListAdapter(documents, actions);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            System.out.println("==> ERROR: "+e.getMessage());
        }
    }
}