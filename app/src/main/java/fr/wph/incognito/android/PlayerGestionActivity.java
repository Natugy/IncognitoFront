package fr.wph.incognito.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.wph.incognito.android.databinding.ActivityMainBinding;
import fr.wph.incognito.android.databinding.ActivityPlayerGestionBinding;
import fr.wph.incognito.core.api.Player;

public class PlayerGestionActivity extends AppCompatActivity {

    private String player;
    private ActivityPlayerGestionBinding ui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityPlayerGestionBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        IncognitoApplication context = (IncognitoApplication) getApplicationContext();
        player = context.getPlayerList().get(getIntent().getIntExtra("playerposition",0));
        ui.editNamePlayer.setText(player);
        ui.confirmBtn.setOnClickListener(v -> {
            player = ui.editNamePlayer.getText().toString();
            context.getPlayerList().set(getIntent().getIntExtra("playerposition",0),player);
            Intent intent = new Intent();
            //intent.putExtra("updated_player", player);
            intent.putExtra("position", getIntent().getIntExtra("playerposition", -1));
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}