package fr.wph.incognito.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.wph.incognito.android.databinding.ActivityWordShowBinding;
import fr.wph.incognito.core.api.Player;

public class WordShowActivity extends AppCompatActivity {

    private ActivityWordShowBinding ui;
    private Player player;
    private String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityWordShowBinding.inflate(getLayoutInflater());
        IncognitoApplication context = (IncognitoApplication) getApplicationContext();
        player = context.getGamePlayerList().get(getIntent().getIntExtra("playerposition",0));
        word = context.game.getWord(player);
        ui.PlayerName.setText(player.name());
        ui.WordLbl.setText("**********");
        setContentView(ui.getRoot());
        ui.button2.setOnClickListener(v -> {
            Intent intent = new Intent();
            //intent.putExtra("updated_player", player);
            intent.putExtra("position", getIntent().getIntExtra("playerposition", -1));
            setResult(RESULT_OK,intent);
            finish();
        });
        ui.imageButton.setOnClickListener(v -> {
            if(word.isEmpty()){
                word = "Tu es M.Blanc !";
            }
           ui.WordLbl.setText(word);
        });
    }
}