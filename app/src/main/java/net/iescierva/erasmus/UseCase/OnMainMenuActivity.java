package net.iescierva.erasmus.UseCase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.iescierva.erasmus.View.LoginActivity;

import java.util.UUID;

public class OnMainMenuActivity {
    private Context contextMainMenuActivity;

    private static final int STORAGE_PERMISSION_CODE = 123;

    public OnMainMenuActivity(Context contextMainMenuActivity) {
        this.contextMainMenuActivity = contextMainMenuActivity;
    }

    public String getFilePath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] arr = { MediaStore.Images.Media.DATA };
            cursor = contextMainMenuActivity.getContentResolver().query(uri,  arr, null, null, null);
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

            new MultipartUploadRequest(contextMainMenuActivity, uploadId, "http://192.168.7.221/api/uploadfile")
                    .addFileToUpload(path, "file")
                    .addHeader("Authorization", "Bearer "+ LoginActivity.user.getApiToken())
                    .setMaxRetries(2)
                    .startUpload();

        } catch (Exception exc) {
            Toast.makeText(contextMainMenuActivity, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(contextMainMenuActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) contextMainMenuActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(contextMainMenuActivity, "Si no aceptas los permisos, no podrías subir documentos", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions((Activity) contextMainMenuActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}
