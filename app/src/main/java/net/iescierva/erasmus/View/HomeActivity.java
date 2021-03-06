// Author ==> Alberto Pérez Fructuoso
// File   ==> HomeActivity.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Actividad principal donde se instancia y adapta tanto la pestaña de
 * datos del usuario (UserFragment.java) como la pestaña de documentos (DocumentFragment.java).
 */
public class HomeActivity extends AppCompatActivity {
    private Actions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actions = new Actions(this);
        actions.requestStoragePermission();

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        UserFragment userFragment = new UserFragment();
        DocumentFragment documentFragment = new DocumentFragment();
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        // Se configuran las pestañas para cada sección (Datos del usuario | Listado de documentos)
        viewPagerAdapter.addFragment(userFragment, String.valueOf(R.string.txt_user_data));
        viewPagerAdapter.addFragment(documentFragment, String.valueOf(R.string.txt_documents_list));
        viewPager.setAdapter(viewPagerAdapter);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(R.string.txt_user_data);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.user_folder);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText(R.string.txt_documents_list);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.document_folder);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }

    /**
     * Este método crea el menu de opciones en la barra de herramientas.
     * @param menu El menu sobre el que añadir las opciones.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * Este método se encarga de ejecutar la función de la opción del menu que fue seleccionada.
     * @param item La opción del menu que fue seleccionada.
     */
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.upload_file_action) {
            showFileChooser();
            return true;
        }
        if (id == R.id.user_save_action) {
            Intent launchEditActivity = new Intent(this, EditActivity.class);
            startActivity(launchEditActivity);
            return true;
        }
        if (id == R.id.back_action) {
            Intent launchAboutActivity = new Intent(this, AboutActivity.class);
            startActivity(launchAboutActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                actions.uploadMultipart(actions.getFilePath(uri));
            }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Este método se usa para mostrar el gestor de archivos y permitir al usuario seleccionar un fichero.
     */
    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            int CHOOSE_FILE = 1;
            startActivityForResult(
                    Intent.createChooser(intent, ""), CHOOSE_FILE);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this.getApplicationContext(), R.string.error_without_file_manager,
                    Toast.LENGTH_SHORT).show();
        }
    }
}