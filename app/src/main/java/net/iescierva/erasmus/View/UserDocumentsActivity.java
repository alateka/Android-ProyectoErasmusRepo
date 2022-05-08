package net.iescierva.erasmus.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import net.iescierva.erasmus.Model.Document;
import net.iescierva.erasmus.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;

public class UserDocumentsActivity extends AppCompatActivity {

    private final int CHOOSE_FILE = 1;

    private OnMainMenuActivity onMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_documents);

        onMainMenu = new OnMainMenuActivity(this);

        onMainMenu.requestStoragePermission();

        Document[] documents = new Document[] {
                new Document(1, "Patata"),
                new Document(2, "Pescado"),
                new Document(3, "Jamón"),
                new Document(4, "Patata"),
                new Document(5, "Filete"),
                new Document(6, "Pescado"),
                new Document(7, "Patata"),
                new Document(8, "Filete"),
                new Document(9, "Pescado"),
        };

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        DocumentListAdapter adapter = new DocumentListAdapter(documents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.upload_file_action) {
            showFileChooser();
            return true;
        }
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
}