package com.example.gerenciadorprojetos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchSugerir;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        switchSugerir = findViewById(R.id.switch_sugerir);
        pref = getSharedPreferences("config_app", MODE_PRIVATE);

        boolean ativo = pref.getBoolean("sugerir_nome", false);
        switchSugerir.setChecked(ativo);

        switchSugerir.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pref.edit().putBoolean("sugerir_nome", isChecked).apply();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}