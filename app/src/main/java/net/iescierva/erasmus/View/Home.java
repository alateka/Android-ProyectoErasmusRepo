package net.iescierva.erasmus.View;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import net.gotev.uploadservice.UploadNotificationConfig;
import net.iescierva.erasmus.R;
import net.iescierva.erasmus.UseCase.OnMainMenuActivity;

public class Home extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserFragment userFragment;

    private final int CHOOSE_FILE = 1;

    private OnMainMenuActivity onMainMenu;

    private DocumentFragment documentFragment;

    private Intent launchEditActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        onMainMenu = new OnMainMenuActivity(this.getApplicationContext());
        onMainMenu.requestStoragePermission();

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        userFragment = new UserFragment();
        documentFragment = new DocumentFragment();
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(userFragment, String.valueOf(R.string.txt_user_data));
        viewPagerAdapter.addFragment(documentFragment, String.valueOf(R.string.txt_documents_list));
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setText(R.string.txt_user_data);
        tabLayout.getTabAt(1).setText(R.string.txt_documents_list);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();
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

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.upload_file_action) {
            showFileChooser();
            return true;
        }
        if (id == R.id.user_edit_action) {
            launchEditActivity = new Intent(this, EditActivity.class);
            startActivity(launchEditActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, ""), CHOOSE_FILE);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this.getApplicationContext(), R.string.error_without_file_manager,
                    Toast.LENGTH_SHORT).show();
        }
    }
}