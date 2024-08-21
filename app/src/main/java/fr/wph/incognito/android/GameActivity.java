package fr.wph.incognito.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.wph.incognito.android.databinding.ActivityGameBinding;
import fr.wph.incognito.core.api.Player;


public class GameActivity extends AppCompatActivity {
    private List<Player> playerList;
    private ActivityGameBinding ui;
    private ActivityResultLauncher<Intent> editPlayerLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        IncognitoApplication context = (IncognitoApplication) getApplicationContext();
        playerList = context.game.getPlayerList();
        ui.recycler.setHasFixedSize(false);
        LinearLayoutManager lm = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, // direction
                false); // sens
        ui.recycler.setLayoutManager(lm);
        editPlayerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        //Intent data = result.getData();
                        //Player updatedPlayer = data.getParcelableExtra("updated_player");
                        //int position = data.getIntExtra("position", -1);
                        //if (position != -1) {
                            //playerList.set(position, updatedPlayer);  // Met à jour le joueur dans la liste
                           // ui.recycler.getAdapter().notifyItemChanged(position);
                          //  //adapter.notifyDataSetChanged();  // Notifie l'adaptateur du changement
                        //}
                    }
                }
        );



        GamePlayerItemAdapter adapter = new GamePlayerItemAdapter(playerList,editPlayerLauncher);
        ui.recycler.setAdapter(adapter);

    }
}