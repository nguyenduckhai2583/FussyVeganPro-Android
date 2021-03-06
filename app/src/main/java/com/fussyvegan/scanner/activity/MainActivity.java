package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.fussyvegan.scanner.BaseContainerFragment;
import com.fussyvegan.scanner.FavoriteFragment;
import com.fussyvegan.scanner.NonSwipeViewPager;
import com.fussyvegan.scanner.ProductFragment;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ScanFragment;
import com.fussyvegan.scanner.container.MoreContainerFragment;
import com.fussyvegan.scanner.container.ProductsContainerFragment;
import com.fussyvegan.scanner.container.ScanContainerFragment;
import com.fussyvegan.scanner.container.TravelContainerFragment;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.search.FilterSearchDialogFragment;
import com.fussyvegan.scanner.search.FilterSearchResortFragment;
import com.fussyvegan.scanner.search.ResortFilter;
import com.fussyvegan.scanner.utils.MessageEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FavoriteFragment.OnFragmentInteractionListener {

    public String keyword = "";
    public String searchScope = "";

    public BottomNavigationView navigation;
    public NonSwipeViewPager viewPager;
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
    int mPosition = 0;
    List<String> mTags = new ArrayList<>();
    List<HomePageItem> tabItems = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    mPosition = 0;
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_advanced:
                    mPosition = 2;
                    viewPager.setCurrentItem(1);

                    return true;
                case R.id.navigation_travel:
                    mPosition = 3;
//                    loadFragmentBy(3);
                    viewPager.setCurrentItem(2);

                    return true;
                case R.id.navigation_setting:
                    mPosition = 4;
                    viewPager.setCurrentItem(3);
                    EventBus.getDefault().post(new MessageEvent("alo"));

//                    loadFragmentBy(4);
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
        viewPager = findViewById(R.id.viewPager);
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
                if(getCurrentBaseFragment() instanceof TravelContainerFragment){
                    showFilterSearchResortDialog();

                }else{
                    showFilterSearchDialog();
                }
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
        initViewPager();
    }


    void initViewPager() {
        tabItems.add(new HomePageItem(new ScanContainerFragment()));
        tabItems.add(new HomePageItem(new ProductsContainerFragment()));
        tabItems.add(new HomePageItem(new TravelContainerFragment()));
        tabItems.add(new HomePageItem(new MoreContainerFragment()));

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this, tabItems);
        viewPager.setVerticalScrollBarEnabled(false);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(tabItems.size());

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

    public void showFilterSearchResortOnly() {
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

    private void showFilterSearchDialog() {
        FilterSearchDialogFragment filterSearchDialogFragment;
        filterSearchDialogFragment = FilterSearchDialogFragment.newInstance();
        filterSearchDialogFragment.show(fm, "");
    }


    private void showFilterSearchResortDialog() {
        FilterSearchResortFragment filterSearchResortFragment = FilterSearchResortFragment.newInstance();
        filterSearchResortFragment.show(fm, "");
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

    public BaseContainerFragment getCurrentBaseFragment() {
        return (BaseContainerFragment) viewPager.getAdapter()
                .instantiateItem(viewPager, viewPager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        BaseContainerFragment f = getCurrentBaseFragment();
        if (f != null) {
            isPopFragment = f.popFragment();
        }
        if (isPopFragment) {

            if (fm == null) {
                return;
            } else {
                fm.popBackStack();
            }

        } else {
            ActivityCompat.finishAffinity(this);
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

    }

    @Override
    public void onFragmentInteraction(Product product) {

    }
}
