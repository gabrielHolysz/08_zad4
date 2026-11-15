package com.example.inte4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    private String[] items;
    private TextView tvResult;
    private String currentSelected = "";
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);
        Button btnRedraw = findViewById(R.id.btnRedraw);
        Button btnChoose = findViewById(R.id.btnChoose);


        if (getIntent() != null && getIntent().hasExtra(MainActivity.EXTRA_ITEMS)) {
            items = getIntent().getStringArrayExtra(MainActivity.EXTRA_ITEMS);
        }

        if (items != null && items.length > 0) {
            drawRandom();
        }


        btnRedraw.setOnClickListener(v -> {
            if (items != null && items.length > 0) {
                drawRandom();
            }
        });


        btnChoose.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainActivity.EXTRA_SELECTED, currentSelected);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void drawRandom() {
        int index = random.nextInt(items.length);
        currentSelected = items[index];
        String text = getString(R.string.result_format, currentSelected, items.length);
        tvResult.setText(text);
    }
}