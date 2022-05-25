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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class OnMainMenuActivity {
    private Context contextMainMenuActivity;

    private String uploadRequest;

    private JSONObject data;
    private JSONArray ciclos;

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

            uploadRequest = new MultipartUploadRequest(contextMainMenuActivity, uploadId, App.IP+"/api/upload_file")
                    .addFileToUpload(path, "file")
                    .addHeader("Authorization", "Bearer "+ App.user.getApiToken())
                    .setMaxRetries(2)
                    .startUpload();

            System.out.println("==> Upload Completed");
            Toast.makeText(contextMainMenuActivity, R.string.txt_update_document,Toast.LENGTH_LONG).show();

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

    public void reloadDocuments()
    {
        RequestQueue queue = Volley.newRequestQueue(contextMainMenuActivity);
        String url = App.IP+"/api/document_list";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                        try {
                            data = new JSONObject(response);
                            App.user.setDocumentList(data.getJSONArray("Data"));
                            Toast.makeText(contextMainMenuActivity, R.string.txt_refresh,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    },
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+App.user.getApiToken());
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public ArrayList<String> getCycles(ArrayList<String> spinnerItems)
    {
        RequestQueue queue = Volley.newRequestQueue(contextMainMenuActivity);
        String url = App.IP+"/api/cycle_list";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        ciclos = new JSONArray(response);

                        for (int i = 0; i < ciclos.length(); i++)
                            if ( !ciclos.getJSONObject(i).getString("Name").contains(App.user.getCycleName()) )
                                spinnerItems.add(ciclos.getJSONObject(i).getString("Name"));

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+App.user.getApiToken());
                return headers;
            }
        };
        queue.add(stringRequest);
        return spinnerItems;
    }

    public void updateUser(String name, String DNI, String lastName, String email, String cycleSelected, String birthDate, String nationality, String locality, String phone, String address, String zip)
    {
        RequestQueue queue = Volley.newRequestQueue(contextMainMenuActivity);
        String url = App.IP+"/api/update_user";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    System.out.println(response);
                },
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+App.user.getApiToken());
                return headers;
            }
            @Override
            public Map<String,String> getParams() {
                Map<String,String> headers = new HashMap<>();
                headers.put("name", name);
                headers.put("last_name", lastName);
                headers.put("email", email);
                headers.put("birth_date", birthDate);
                headers.put("cycle_name", cycleSelected);
                headers.put("dni", DNI);
                headers.put("nationality", nationality);
                headers.put("phone", phone);
                headers.put("locality", locality);
                headers.put("address", address);
                headers.put("zip", zip);
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void deleteDocumentByID(String id)
    {
        RequestQueue queue = Volley.newRequestQueue(contextMainMenuActivity);
        String url = App.IP+"/api/remove_document";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    System.out.println(response);
                },
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+App.user.getApiToken());
                return headers;
            }
            @Override
            public Map<String,String> getParams() {
                Map<String,String> headers = new HashMap<>();
                headers.put("id", id);
                return headers;
            }
        };
        queue.add(stringRequest);
    }
}
