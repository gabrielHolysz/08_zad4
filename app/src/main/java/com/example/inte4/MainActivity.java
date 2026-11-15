package com.example.inte4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ITEMS = "extra_items";
    public static final String EXTRA_SELECTED = "extra_selected";

    private EditText etItem;
    private TextView tvList;
    private final List<String> itemsList = new ArrayList<>();

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String selectedItem = result.getData().getStringExtra(EXTRA_SELECTED);
                    if (selectedItem != null && !selectedItem.isEmpty()) {
                        // Pokazanie wybranego wpisu w Snackbarze po powrocie
                        Snackbar.make(findViewById(R.id.main),
                                getString(R.string.selected_snack, selectedItem),
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItem = findViewById(R.id.etItem);
        tvList = findViewById(R.id.tvList);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnDraw = findViewById(R.id.btnDraw);

        updateListDisplay();

        btnAdd.setOnClickListener(v -> {
            String text = etItem.getText().toString().trim();
            if (text.isEmpty()) {
                etItem.setError(getString(R.string.error_empty_input));
                return;
            }
            itemsList.add(text);
            etItem.setText("");
            updateListDisplay();
        });

        btnDraw.setOnClickListener(v -> {
            // Zabezpieczenie przed pustą listą (komunikat zamiast awarii)
            if (itemsList.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_list, Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            // Przekazanie tablicy napisów String[] przez putExtra
            String[] itemsArray = itemsList.toArray(new String[0]);
            intent.putExtra(EXTRA_ITEMS, itemsArray);
            resultLauncher.launch(intent);
        });
    }

    private void updateListDisplay() {
        if (itemsList.isEmpty()) {
            tvList.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itemsList.size(); i++) {
            sb.append((i + 1)).append(". ").append(itemsList.get(i)).append("\n");
        }
        tvList.setText(sb.toString().trim());
    }
}