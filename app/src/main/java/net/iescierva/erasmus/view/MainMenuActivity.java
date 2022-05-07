package net.iescierva.erasmus.view;
import net.iescierva.erasmus.R;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class MainMenuActivity extends AppCompatActivity {
    private final int CHOOSE_FILE = 1;
    private Button buttonChoose;

    private static final int STORAGE_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonChoose = findViewById(R.id.buttonChoose);

        buttonChoose.setOnClickListener(view -> showFileChooser());
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
                    uploadMultipart(getFilePath(this, uri));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFilePath(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] arr = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(uri,  arr, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void uploadMultipart(String path) {

        try {
            String uploadId = UUID.randomUUID().toString();

            new MultipartUploadRequest(this, uploadId, "http://192.168.7.111/api/uploadfile")
                    .addFileToUpload(path, "file") //Adding file
                    .addHeader("Authorization", "Bearer 25|GV8xEZKsXKQ95SDIzePjfiuG8m6hXx4gAOY1Nwc9")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Si no aceptas los permisos, no podrías subir documentos", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}