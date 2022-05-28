package net.iescierva.erasmus.UseCase;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.iescierva.erasmus.App;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.utils.InputStreamVolleyRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnMainMenuActivity {
    private final Context contextActivity;

    private JSONObject data;
    private JSONArray ciclos;

    public String uploadRequest;

    private static final int STORAGE_PERMISSION_CODE = 123;

    public OnMainMenuActivity(Context contextActivity) {
        this.contextActivity = contextActivity;
    }

    public String getFilePath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] arr = { MediaStore.Images.Media.DATA };
            cursor = contextActivity.getContentResolver().query(uri,  arr, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void uploadMultipart(String path) {
        try {
            String uploadId = UUID.randomUUID().toString();

            uploadRequest = new MultipartUploadRequest(contextActivity, uploadId, App.IP + "/api/upload_file")
                    .addFileToUpload(path, "file")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .addHeader("Authorization", "Bearer " + App.user.getApiToken())
                    .setMaxRetries(2)
                    .startUpload();

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            Toast.makeText(contextActivity, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(contextActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) contextActivity, Manifest.permission.READ_EXTERNAL_STORAGE))
            Toast.makeText(contextActivity, "Si no aceptas los permisos, no podrías subir documentos", Toast.LENGTH_SHORT).show();

        ActivityCompat.requestPermissions((Activity) contextActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void reloadDocuments()
    {
        RequestQueue queue = Volley.newRequestQueue(contextActivity);
        String url = App.IP+"/api/document_list";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                        try {
                            data = new JSONObject(response);
                            App.user.setDocumentList(data.getJSONArray("Data"));
                            Toast.makeText(contextActivity, R.string.txt_refresh,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    },
                error -> System.out.println("ERROR ==> "+error.getMessage())){
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
        RequestQueue queue = Volley.newRequestQueue(contextActivity);
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
                error -> System.out.println("ERROR ==> "+error.getMessage())){
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
        RequestQueue queue = Volley.newRequestQueue(contextActivity);
        String url = App.IP+"/api/update_user";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                System.out::println,
                error -> System.out.println("ERROR ==> "+error.getMessage())){
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
        RequestQueue queue = Volley.newRequestQueue(contextActivity);
        String url = App.IP+"/api/remove_document?id="+id;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> Toast.makeText(contextActivity, R.string.txt_document_deleted, Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(contextActivity, "ERROR :(", Toast.LENGTH_LONG).show()){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+App.user.getApiToken());
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void downloadAndOpenPDF(String id, String name) {

        String url = App.IP+"/api/download_document?id="+id;

        // HTTP Headers
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+App.user.getApiToken());

        Toast.makeText(contextActivity, R.string.message_open_pdf, Toast.LENGTH_LONG).show();

        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, url,
                response -> {
                    try {
                        if (response != null) {
                            FileOutputStream outputStream;
                            String tempPDF = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + name;
                            outputStream = new FileOutputStream(tempPDF);
                            outputStream.write(response);
                            outputStream.close();

                            File file = new File(tempPDF);
                            Uri uriPdfPath = FileProvider.getUriForFile(contextActivity, contextActivity.getApplicationContext().getPackageName() + ".provider", file);

                            Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
                            pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
                            pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
                            pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                            try {
                                contextActivity.startActivity(pdfOpenIntent);
                            } catch (ActivityNotFoundException activityNotFoundException) {
                                Toast.makeText(contextActivity,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(contextActivity, "ERROR :(", Toast.LENGTH_LONG).show(),
                headers
        );
        RequestQueue mRequestQueue = Volley.newRequestQueue(contextActivity.getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
    }
}
