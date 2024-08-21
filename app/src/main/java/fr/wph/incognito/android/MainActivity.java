package fr.wph.incognito.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.wph.incognito.android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding ui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        Button startBtn = ui.startBtn;
        startBtn.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View view) {
        finish();
        Intent intent = new Intent(this,PlayerListActivity.class);
        startActivity(intent); // lance la new activity et stoppe la main

    }
}