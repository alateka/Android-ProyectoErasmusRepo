package net.iescierva.erasmus.View;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.Model.User;
import net.iescierva.erasmus.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDocumentsActivity extends AppCompatActivity {

    private final int CHOOSE_FILE = 1;

    private OnMainMenuActivity onMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_documents);

        onMainMenu = new OnMainMenuActivity(this);
        onMainMenu.requestStoragePermission();

        try {
            Document[] documents = new Document[LoginActivity.user.getDocumentList().length()];

            for (int i = 0; i < LoginActivity.user.getDocumentList().length(); i++) {
                documents[i] = new Document(i, LoginActivity.user.getDocumentList().getJSONObject(i).getString("documento"));
            }
            DocumentListAdapter adapter = new DocumentListAdapter(documents);

            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            System.out.println("ERROR ==> "+e.getMessage());
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.upload_file_action) {
            showFileChooser();
            return true;
        }
        if (id == R.id.switch_user_action) {
            Intent i = new Intent(this, UserDataActivity.class);
            startActivity(i);
            return true;
        }
        /*if (id == R.id.refresh_documents) {
            refreshDocumentList();
            Intent i = new Intent(this, UserDocumentsActivity.class);
            startActivity(i);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.document_menu, menu);
        return true;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, ""), CHOOSE_FILE);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.error_without_file_manager,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    onMainMenu.uploadMultipart(onMainMenu.getFilePath(uri));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*private void refreshDocumentList() {

        RequestQueue queue = Volley.newRequestQueue(UserDocumentsActivity.this);
        String url = "http://192.168.7.111/api/documentlist";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> reloadDocuments(response),
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+ LoginActivity.user.getApiToken());
                return params;
            }
        };
        queue.add(stringRequest);
    }*/

    /*private void reloadDocuments(String response) {
        try {
            JSONObject jsonData = new JSONObject(response);
            LoginActivity.user.setDocumentList(jsonData.getJSONArray("Documents"));

            System.out.println("==> OK :: Started User Session");
            Intent i = new Intent(this, UserDocumentsActivity.class);
            startActivity(i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }*/
}