package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.AdvancedSearchMenuFragment;
import com.fussyvegan.scanner.FastfoodFragment;
import com.fussyvegan.scanner.FavoriteFragment;
import com.fussyvegan.scanner.SearchFragment;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ScanFragment;
import com.fussyvegan.scanner.SettingFragment;
import com.fussyvegan.scanner.ProductFragment;
import com.fussyvegan.scanner.search.FilterSearchDialogFragment;

public class MainActivity extends AppCompatActivity implements FavoriteFragment.OnFragmentInteractionListener {

    public String keyword = "";
    public String searchScope = "";

    public BottomNavigationView navigation;
    public Menu menu;
    public MenuItem editItem;
    public MenuItem favoriteItem;
    public MenuItem flashItem;
    public MenuItem filterItem;

    public ImageView imgMenu;
    public ImageView imgFavorite;
    public ImageView imgFlash;
    public ImageView imgFilterSearch;
    public TextView tvEdit;
    String tag = "";
    //    public ActionBar actionBar;
    public boolean showEditItem = false;
    public boolean showFavoriteItem = false;
    public boolean showFlash = false;
    public boolean showFilterSearch = false;
    public Button btnSetting;
    public static final int PERMISSIONS_REQUEST_CAMERA_CODE = 55555;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    loadFragmentBy(0);
                    return true;
                case R.id.navigation_search:
                    loadFragmentBy(1);
                    return true;
                case R.id.navigation_advanced:
                    loadFragmentBy(2);
                    return true;
                case R.id.navigation_fastfood:
                    loadFragmentBy(3);
                    return true;
                case R.id.navigation_setting:
                    loadFragmentBy(4);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        actionBar = getSupportActionBar();
        btnSetting = findViewById(R.id.btnSetting);
        imgMenu = findViewById(R.id.imgRight);
        imgFavorite = findViewById(R.id.imgFavorite);
        imgFlash = findViewById(R.id.imgFlash);
        imgFilterSearch = findViewById(R.id.imgFilter);
        tvEdit = findViewById(R.id.tvEdit);
        imgFavorite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductFragment pf = (ProductFragment) getSupportFragmentManager().findFragmentByTag("ProductFragment");
                        if (pf != null && pf.isVisible())
                            pf.addToFavorite();
                    }
                }
        );
        imgFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanFragment sf = (ScanFragment) getSupportFragmentManager().findFragmentByTag("ScanFragment");
                if (sf != null && sf.isVisible()) {
                    sf.switchFlash();
                }
                if (sf.mFlash) {
                    imgFlash.setImageResource(R.drawable.ic_flash_on);
                } else {
                    imgFlash.setImageResource(R.drawable.ic_flash_off);
                }
            }
        });

        imgFilterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterSearchDialog();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          FavoriteFragment ff = (FavoriteFragment) getSupportFragmentManager().findFragmentByTag("ProductKeywordFragment");
                                          if (ff != null && ff.isVisible()) {
                                              ff.edit();
                                          }

                                          if (ff.isEdit) {
                                              tvEdit.setText("Done");
                                          } else {
                                              tvEdit.setText("Edit");
                                          }
                                      }
                                  }
        );
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInstalledAppDetailsActivity(MainActivity.this);
            }
        });
        loadFragmentBy(0);
        visibleCameraSetting(false);
        requestAccessCamera();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        editItem = menu.findItem(R.id.action_edit);
        favoriteItem = menu.findItem(R.id.action_favorite);
        flashItem = menu.findItem(R.id.action_flash);
        filterItem = menu.findItem(R.id.action_filter);

        editItem.setVisible(showEditItem);
        favoriteItem.setVisible(showFavoriteItem);
        flashItem.setVisible(showFlash);
        filterItem.setVisible(showFilterSearch);

        this.menu = menu;
        return true;
    }

    public void visibleCameraSetting(boolean val) {
        if (val) {
            btnSetting.setVisibility(View.VISIBLE);
        } else {
            btnSetting.setVisibility(View.GONE);
        }
    }

    public void showOnlyFlash() {
        imgFlash.setVisibility(View.VISIBLE);
        imgFavorite.setVisibility(View.GONE);
        imgMenu.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        imgFilterSearch.setVisibility(View.GONE);
    }

    public void showFilterSearchOnly() {
        imgFlash.setVisibility(View.GONE);
        imgFavorite.setVisibility(View.GONE);
        imgMenu.setVisibility(View.GONE);
        imgFilterSearch.setVisibility(View.VISIBLE);

        tvEdit.setVisibility(View.GONE);
        showEditItem = false;
        showFavoriteItem = false;
        showFlash = false;
        showFilterSearch = true;
    }

    public void showOnlyEdit() {
        imgFlash.setVisibility(View.GONE);
        imgFavorite.setVisibility(View.GONE);
        imgMenu.setVisibility(View.GONE);
        imgFilterSearch.setVisibility(View.GONE);
        tvEdit.setVisibility(View.VISIBLE);
        showEditItem = true;
        showFavoriteItem = false;
        showFlash = false;
    }

    public void showOnlyFavorite() {
        imgFlash.setVisibility(View.GONE);
        imgFilterSearch.setVisibility(View.GONE);
        imgFavorite.setVisibility(View.VISIBLE);
        imgMenu.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
    }

    public void showNothing() {
        imgFlash.setVisibility(View.GONE);
        imgFavorite.setVisibility(View.GONE);
        imgMenu.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        imgFilterSearch.setVisibility(View.GONE);
    }

    public void requestAccessCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Log.d("TAG", "shouldShowRequestPermissionRationale");
                visibleCameraSetting(true);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA_CODE);
                Log.d("TAG", "requestPermissions");
            }
        } else {
            Log.d("TAG", "Permission has already been granted");
            visibleCameraSetting(false);
        }
    }

    FragmentManager fm = getSupportFragmentManager();
    FilterSearchDialogFragment filterSearchDialogFragment;
    private void showFilterSearchDialog() {
        filterSearchDialogFragment = FilterSearchDialogFragment.newInstance();
        filterSearchDialogFragment.show(fm, "");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "PERMISSIONS_REQUEST_CAMERA_CODE OK");
                } else {
                    Log.d("TAG", "PERMISSIONS_REQUEST_CAMERA_CODE FAILED");
                }
                return;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
                break;
            case R.id.action_favorite:
                //Toast.makeText(this, "Favorite selected", Toast.LENGTH_SHORT).show();
                ProductFragment pf = (ProductFragment) getSupportFragmentManager().findFragmentByTag("ProductFragment");
                if (pf != null && pf.isVisible())
                    pf.addToFavorite();
                break;
            case R.id.action_edit:
                //Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();
                FavoriteFragment ff = (FavoriteFragment) getSupportFragmentManager().findFragmentByTag("ProductKeywordFragment");
                if (ff != null && ff.isVisible()) {
                    ff.edit();
                }

                if (ff.isEdit) {
                    item.setTitle("Done");
                } else {
                    item.setTitle("Edit");
                }
                break;
            case R.id.action_flash:
                //Toast.makeText(this, "Flash selected", Toast.LENGTH_SHORT).show();
                ScanFragment sf = (ScanFragment) getSupportFragmentManager().findFragmentByTag("ScanFragment");
                if (sf != null && sf.isVisible()) {
                    sf.switchFlash();
                }
                if (sf.mFlash) {
                    item.setIcon(R.drawable.ic_flash_on);
                } else {
                    item.setIcon(R.drawable.ic_flash_off);
                }
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

//    @Override
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void OnListFragmentInteractionListener(Uri uri);
//    }

    public void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public void visibleBackItem(boolean val) {
//        actionBar.setHomeButtonEnabled(val);
//        actionBar.setDisplayShowHomeEnabled(val);
//        actionBar.setDisplayHomeAsUpEnabled(val);
//        actionBar.setIcon(R.drawable.ic_launcher);
    }

    public void loadFragmentBy(int type) {
        if (findViewById(R.id.fragment_container) != null) {
            Fragment fragment = new Fragment();
            switch (type) {
                case 0:
                    fragment = new ScanFragment();
                    tag = "ScanFragment";
                    break;
                case 1:
                    fragment = new SearchFragment();
                    tag = "SearchFragment";
                    break;
                case 2:
                    fragment = new AdvancedSearchMenuFragment();
                    tag = "AdvancedSearchMenuFragment";
                    break;
                case 3:
                    fragment = new FastfoodFragment();
                    tag = "FastfoodFragment";
                    break;
                case 4:
                    fragment = new SettingFragment();
                    tag = "SettingFragment";
                    break;
            }
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Product product) {

    }
}
