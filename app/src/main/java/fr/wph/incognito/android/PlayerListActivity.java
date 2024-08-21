package fr.wph.incognito.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import fr.wph.incognito.android.databinding.ActivityEditBinding;
import fr.wph.incognito.core.api.Game;
import fr.wph.incognito.core.api.Player;
import fr.wph.incognito.core.api.Role;

public class PlayerListActivity extends AppCompatActivity {

    private ActivityEditBinding ui;
    private List<String> playerList;
    private ActivityResultLauncher<Intent> editPlayerLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        IncognitoApplication context = (IncognitoApplication) getApplicationContext();
        playerList = context.getPlayerList();
        ui.editIncognito.setText("1");
        ui.editWhite.setText("0");
        LinearLayoutManager lm = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, // direction
                false); // sens
        ui.recycler.setLayoutManager(lm);
        ui.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPlayer = "Joueur";
                playerList.add(newPlayer);
                Intent intent = new Intent(context, PlayerGestionActivity.class);
                intent.putExtra("playerposition", playerList.size() - 1);
                editPlayerLauncher.launch(intent);
                //finish();// lance la new activity et stoppe la main
                //adapter.notifyItemInserted(playerList.size());


            }
        });
        editPlayerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        int position = data.getIntExtra("position", -1);
                        if (position != -1) {
                            //playerList.set(position, updatedPlayer);  // Met à jour le joueur dans la liste
                            ui.recycler.getAdapter().notifyItemChanged(position);
                            //adapter.notifyDataSetChanged();  // Notifie l'adaptateur du changement
                        }
                    }
                }
        );
        ui.recycler.setHasFixedSize(false);
        ui.button.setOnClickListener(
                v -> {
                    // 1. Instantiate an AlertDialog.Builder with its constructor.
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
// 2. Chain together various setter methods to set the dialog characteristics.
                    builder.setMessage(R.string.dialog_start_game);
                    builder.setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HashSet<String> playerHashSet = new HashSet<>(playerList);
                            HashMap<Role,Integer> roleMap = new HashMap<>(Map.of(Role.INCOGNITO,Integer.parseInt(ui.editIncognito.getText().toString())));
                            roleMap.put(Role.WHITE,Integer.parseInt(ui.editWhite.getText().toString()));
                            List<Player> gamePlayerList = new ArrayList<>();
                            try {
                               gamePlayerList = context.game.startGame(playerHashSet,roleMap);
                                context.setGamePlayerList(gamePlayerList);
                                Log.i("list",gamePlayerList.toString());
                            } catch (Exception e) {
                                AlertDialog errorDialog = new AlertDialog.Builder(v.getContext()).setMessage(e.getMessage()).create();
                                errorDialog.show();

                                Log.e("error", "onClick: "+e.getMessage() + " " + e.getStackTrace()[0]);
                                Log.d("error", "nbIncognito: "+ui.editIncognito.getText().toString());
                                return;
                            }

                            finish();
                            Intent intent = new Intent(context, GameActivity.class);
                            editPlayerLauncher.launch(intent);
                            for (Player player : context.game.getPlayerList()) {
                                Intent intent2 = new Intent(context, WordShowActivity.class);
                                intent2.putExtra("playerposition", gamePlayerList.indexOf(player));
                                editPlayerLauncher.launch(intent2);
                            }

                        }
                    });
                    builder.setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancels the dialog.
                        }
                    });
// 3. Get the AlertDialog.
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
        );
        PlayerItemAdapter adapter = new PlayerItemAdapter(playerList, editPlayerLauncher);
        ui.recycler.setAdapter(adapter);
    }


}