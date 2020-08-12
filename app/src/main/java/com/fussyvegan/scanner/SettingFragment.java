package com.fussyvegan.scanner;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fussyvegan.scanner.activity.ChangePasswordActivity;
import com.fussyvegan.scanner.activity.ForgotPasswordActivity;
import com.fussyvegan.scanner.activity.LoginActivity;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.SettingAdapter;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SettingFragment extends Fragment {

    public int loginIntentCODE = 0;

    public int forgotPassIntentCODE = 2;

    public int forgotPassIntentCODEFromFragment = 3;
    public int changePasswordCODEFromFragment = 4;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    SettingAdapter adapter;
    List<String> settings = new ArrayList<>();
    List<Integer> icLink = new ArrayList<>();
    ListView ltvSetting;
    MainActivity activity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SettingFragment() {
        settings.add("My List");
        settings.add("About");
        settings.add("Rate App");
        settings.add("Contact Us");
        settings.add("App Support");
        settings.add("Website");
        settings.add("Privacy Policy");
        settings.add("Terms & Conditions");
        if (SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
            settings.add("Change Password");
        } else {
            settings.add("Forgot Password");
        }
        if (SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
            settings.add("Logout");
        } else {
            settings.add("Login/Register");
        }
        icLink.add(R.drawable.ic_mylist_colour);
        icLink.add(R.drawable.ic_about);
        icLink.add(R.drawable.ic_rate);
        icLink.add(R.drawable.ic_email);
        icLink.add(R.drawable.ic_support);
        icLink.add(R.drawable.ic_web);
        icLink.add(R.drawable.ic_privacy);
        icLink.add(R.drawable.ic_terms);
        icLink.add(R.drawable.ic_password);
        icLink.add(R.drawable.ic_login);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SettingFragment newInstance(int columnCount) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        adapter = new SettingAdapter(settings, icLink);
        ltvSetting = view.findViewById(R.id.ltvSetting);
        ltvSetting.setAdapter(adapter);
        ltvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");

                if (position == 0) {
                    String tag = "ProductKeywordFragment";
                //    FavoriteFragment fragment = new FavoriteFragment();
                    FavoriteListFragment fragment = new FavoriteListFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                } else if (position == 1) {
                    String tag = "AboutFragment";
                    AboutFragment fragment = new AboutFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                } else if (position == 2) {
                    Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=com.fussyvegan.pro" + getContext().getPackageName())));
                    }
                } else if (position == 3) {
                    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "info@fussyvegan.com.au", null));
                    getActivity().startActivity(i);
                } else if (position == 4) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fussyvegan.com.au/fussyveganpro_support"));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), "No application can handle this request. Please install a web browser", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else if (position == 5) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fussyvegan.com.au"));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), "No application can handle this request. Please install a web browser", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else if (position == 6) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fussyvegan.com.au/privacy"));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), "No application can handle this request. Please install a web browser", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else if (position == 7) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fussyvegan.com.au/app_terms"));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), "No application can handle this request. Please install a web browser", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else if (position == 8) {
                    if(SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)){
                        startActivityForResult(new Intent(getActivity(), ChangePasswordActivity.class), changePasswordCODEFromFragment);
                    } else {
                        startActivityForResult(new Intent(getActivity(), ForgotPasswordActivity.class), forgotPassIntentCODEFromFragment);
                    }

                } else if (position == 9) {
                    if (SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
                        showLogout();
                    } else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), loginIntentCODE);
                    }
                }
            }
        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        return view;
    }


    private void showLogout() {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefs.getInstance().put(Constant.IS_LOGIN, false);


                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == loginIntentCODE && resultCode == loginIntentCODE && data != null) {
            String mess = data.getStringExtra("key");
            Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();
        } else if (requestCode == forgotPassIntentCODEFromFragment && resultCode == forgotPassIntentCODE && data != null) {
            String mess = data.getStringExtra("key");
            Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();
        } else if(requestCode == changePasswordCODEFromFragment && resultCode == changePasswordCODEFromFragment && data!= null) {
            String mess = data.getStringExtra("key");
            Toast.makeText(getActivity(), mess, Toast.LENGTH_SHORT).show();
        }
    }
}
