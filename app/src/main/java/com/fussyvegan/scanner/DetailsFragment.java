package com.fussyvegan.scanner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fussyvegan.scanner.model.restaurant.Restaurant;

public class DetailsFragment extends Fragment {

    private static final String RESTAURANT = "restaurant";
    private Restaurant restaurant;

    TextView tvDescription, tvTime;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable(RESTAURANT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        tvTime = view.findViewById(R.id.tvTime);
        tvDescription = view.findViewById(R.id.tvDescription);

        tvDescription.setText(restaurant.getDescription());
        tvTime.setText(restaurant.getHours());

        return view;
    }
}