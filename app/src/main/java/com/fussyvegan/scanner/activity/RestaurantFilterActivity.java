package com.fussyvegan.scanner.activity;

import android.content.Intent;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.ResFilterAdapter;
import com.fussyvegan.scanner.model.CuisineType;

import java.util.ArrayList;

public class RestaurantFilterActivity extends AppCompatActivity {

    private static final String DISTANCE = "distance";
    private static final String LIST_CHOOSE = "listChoose";

    ImageView imgBack;
    TextView tvClear, tvDistance;
    SeekBar sbkDistance;
    Button btnApply;
    RecyclerView recyclerCuisine;
    ResFilterAdapter adapter;
    ArrayList<CuisineType> list;
    int distance;
    ArrayList<CuisineType> listChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_filter);

        addContent();
        addEvent();
        prepareData();

    }

    private void prepareData() {
        Intent intent = getIntent();
        distance = intent.getIntExtra(DISTANCE, 0) * 100 / 80;
        sbkDistance.setProgress(distance);
        listChoose = intent.getParcelableArrayListExtra(LIST_CHOOSE);
        for (CuisineType item : list) {
            for (CuisineType itemChoice : listChoose) {
                if (item.getType().equals(itemChoice.getType())) {
                    item.setChecked(itemChoice.isChecked());
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void addEvent() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbkDistance.setProgress(0);
                for (CuisineType cuisineType : list) {
                    cuisineType.setChecked(false);
                }
                listChoose.clear();
                adapter.notifyDataSetChanged();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listChoose.clear();
                for (CuisineType item : list) {
                    if (item.isChecked()) {
                        listChoose.add(item);
                    }
                }

                Intent intent = new Intent();
                intent.putExtra(DISTANCE, distance);
                intent.putExtra(LIST_CHOOSE, listChoose);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        sbkDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distance = i*80/100;
                String text = distance + " km";
                tvDistance.setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void addContent() {
        listChoose = new ArrayList<>();
        imgBack = findViewById(R.id.imgBack);
        btnApply = findViewById(R.id.btnApply);
        tvDistance = findViewById(R.id.tvDistance);
        tvClear = findViewById(R.id.tvClear);
        sbkDistance = findViewById(R.id.sbkDistance);
        recyclerCuisine = findViewById(R.id.recyclerCuisine);
        list = new ArrayList<>();
        adapter = new ResFilterAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerCuisine.setLayoutManager(linearLayoutManager);
        recyclerCuisine.setAdapter(adapter);

        Resources resources = getResources();
        String [] a = resources.getStringArray(R.array.Cuisine_Type);
        for (String item : a) {
            list.add(new CuisineType(item, false));
        }
        adapter.notifyDataSetChanged();
    }
}