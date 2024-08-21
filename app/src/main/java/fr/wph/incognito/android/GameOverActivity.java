package fr.wph.incognito.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.wph.incognito.android.databinding.ActivityGameOverBinding;
import fr.wph.incognito.core.api.Player;
import fr.wph.incognito.core.api.Role;

public class GameOverActivity extends AppCompatActivity {

    private ActivityGameOverBinding ui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityGameOverBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        IncognitoApplication context = (IncognitoApplication) getApplicationContext();
        StringBuilder winner = new StringBuilder("La liste des gagnants est : \n");
        for (Player player : context.game.getWinners()) {
            winner.append(player.name()).append(" : ").append(player.role()+"\n");
        }
        ui.messageGameOver.setText(winner);

        ui.button3.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}