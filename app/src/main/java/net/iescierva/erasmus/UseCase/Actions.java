// Author ==> Alberto Pérez Fructuoso
// File   ==> Actions.java
// Date   ==> 2022/05/29

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
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;
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

public class Actions {
    private final Context contextActivity;

    private JSONObject data;
    private JSONArray ciclos;

    public String uploadRequest;

    private static final int STORAGE_PERMISSION_CODE = 123;

    /**
     * Clase dedicada a los casos de uso y acciones por parte del usuario.
     * @param contextActivity El contexto de la actividad sobre la que se invoca el objeto.
     */
    public Actions(Context contextActivity) {
        this.contextActivity = contextActivity;
    }

    /**
     * Devuelve la ruta completa de un fichero.
     * @return La ruta del fichero, que en este caso son los documentos.
     */
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

    /**
     * Realiza la subida del documento seleccionado al servidor
     * con la ayuda del servicio Android Upload Service (gotev).
     * @param path La ruta del fichero a subir.
     */
    public void uploadMultipart(String path) {
        try {
            uploadRequest = new MultipartUploadRequest(contextActivity, App.IP + "/api/upload_file")
                    .addFileToUpload(path, "file")
                    .addHeader("Authorization", "Bearer " + App.user.getApiToken())
                    .setMaxRetries(2)
                    .startUpload();

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            Toast.makeText(contextActivity, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Control de permisos de escritura.
     */
    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(contextActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) contextActivity, Manifest.permission.READ_EXTERNAL_STORAGE))
            Toast.makeText(contextActivity, "Si no aceptas los permisos, no podrías subir documentos", Toast.LENGTH_SHORT).show();

        ActivityCompat.requestPermissions((Activity) contextActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    /**
     * Refresca el listado de documentos.
     */
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

    /**
     * Añade los ciclos formativos actuales al Spinner en cuestión obteniendolos desde la API.
     * De tal forma que si se añadieran nuevos ciclos a la BD, estos se importarían al spinner sin problemas.
     * @param spinnerItems El spinner al que se desea añadir los ciclos.
     * @return El ArrayList con los ciclos formativos añadidos desde la API.
     */
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

    /**
     * Actualiza los datos actuales del usuario.
     * @param name Nuevo nombre del usuario.
     * @param DNI Nuevo DNI del usuario.
     * @param lastName Nuevo apellidos del usuario.
     * @param email Nuevo correo electrónico del usuario.
     * @param cycleSelected Nombre existente del ciclo formativo.
     * @param birthDate Nuevo fecha de nacimiento del usuario.
     * @param nationality Nueva nacionalidad del usuario.
     * @param locality Nueva localidad del usuario.
     * @param phone Nuevo numero de teléfono del usuario.
     * @param address Nueva dirección del usuario.
     * @param zip Nuevo código postal del usuario.
     */
    public void updateUser(String name, String DNI, String lastName, String email, String cycleSelected, String birthDate, String nationality, String locality, String phone, String address, String zip)
    {
        RequestQueue queue = Volley.newRequestQueue(contextActivity);
        String url = App.IP+"/api/update_user";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    if ( response.contains("OK") ) {
                        Toast.makeText(contextActivity, R.string.txt_modified_data, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    System.out.println("ERROR ==> "+error.getMessage());
                    Toast.makeText(contextActivity, R.string.txt_error_modifying_data, Toast.LENGTH_LONG).show();
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

    /**
     * Elimina un documento del usuario pasando como referencia su ID.
     * @param id ID del documento a borrar.
     */
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

    /**
     * Descarga y visualiza un documento.
     * @param id ID del documento a mostrar.
     * @param name Nombre del documento a mostrar.
     */
    public void downloadAndOpen(String id, String name) {

        String url = App.IP+"/api/download_document?id="+id;

        // Cabeceras HTTP
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+App.user.getApiToken());

        Toast.makeText(contextActivity, R.string.message_open_pdf, Toast.LENGTH_LONG).show();

        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, url,
                response -> {
                    try {
                        if (response != null) {
                        // Si la respuesta contiene datos, se creará un objeto FileOutputStream el cual se guardará como fichero temporalmente
                        // Los datos obtenidos de la respuesta se guardarán en ese fichero para formar el archivo descargado. (PDF, World, Imagen, etc)
                            FileOutputStream outputStream;
                            String tempPDF = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + name;
                            outputStream = new FileOutputStream(tempPDF);
                            outputStream.write(response);
                            outputStream.close();

                            File file = new File(tempPDF);
                            Uri uriPath = FileProvider.getUriForFile(contextActivity,
                                    contextActivity.getApplicationContext().getPackageName() + ".provider", file);

                            Intent openIntent = new Intent(Intent.ACTION_VIEW);
                            openIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            openIntent.setClipData(ClipData.newRawUri("", uriPath));


                            // En función del tipo de archivo se identificará con un tipo de MIME distinto.
                            // De esta manera el sistema de Android abrirá el fichero con la aplicaión que mas le convenga
                            if (name.contains(".pdf"))
                                openIntent.setDataAndType(uriPath, "application/pdf");
                            if (name.contains(".jpeg") || name.contains(".jpg") || name.contains(".png"))
                                openIntent.setDataAndType(uriPath, "image/jpeg");
                            if (name.contains(".txt"))
                                openIntent.setDataAndType(uriPath, "text/plain");
                            if (name.contains(".docx") || name.contains(".odt"))
                                openIntent.setDataAndType(uriPath, "application/vnd.oasis.opendocument.text");
                            if (name.contains(".tar") || name.contains(".zip") || name.contains(".rar") || name.contains(".7z"))
                                openIntent.setDataAndType(uriPath, "application/zip");

                            openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                            try {
                                contextActivity.startActivity(openIntent);
                            } catch (ActivityNotFoundException activityNotFoundException) {
                                Toast.makeText(contextActivity,"ERROR",Toast.LENGTH_LONG).show();
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
