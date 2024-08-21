package fr.wph.incognito.android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.wph.incognito.android.databinding.GamePlayerItemBinding;
import fr.wph.incognito.android.databinding.PlayerItemBinding;
import fr.wph.incognito.core.api.Player;

public class GamePlayerItemAdapter extends RecyclerView.Adapter<GamePlayerItemHolder> {
    private final List<Player> playerList;
    private final ActivityResultLauncher<Intent> editPlayerLauncher;
    GamePlayerItemAdapter(List<Player> playerList, ActivityResultLauncher<Intent> editPlayerLauncher){
        this.playerList = playerList;
        this.editPlayerLauncher = editPlayerLauncher;
    }
    @NonNull
    @Override
    public GamePlayerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GamePlayerItemBinding binding = GamePlayerItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new GamePlayerItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GamePlayerItemHolder holder, int position) {
        Player player = playerList.get(position);
        holder.setPlayer(player);
        holder.ui.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprimer le joueur de la liste
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

// 2. Chain together various setter methods to set the dialog characteristics.
                builder.setMessage("Voulez-vous vraiment éliminer "+player.name()+" ?");
                builder.setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder builderMessage = new AlertDialog.Builder(v.getContext());
                        builderMessage.setMessage("Un "+player.role()+ " est éliminé");
                        AlertDialog dialogMessage = builderMessage.create();
                        dialogMessage.show();
                        //playerList.remove(position);

                        IncognitoApplication context = (IncognitoApplication) v.getContext().getApplicationContext();
                        try {
                            context.game.kill(player);
                            Log.i("list", String.valueOf(context.game.getPlayerList()));
                        }catch (IllegalArgumentException e){
                            AlertDialog errorDialog = new AlertDialog.Builder(v.getContext()).setMessage(e.getMessage()).create();
                            errorDialog.show();
                        }
                        Log.i("list", String.valueOf(context.game.isGameOver()));
                       if(context.game.isGameOver()){
                            Intent intent = new Intent(v.getContext(), GameOverActivity.class);
                            v.getContext().startActivity(intent);
                       }
                        notifyItemRemoved(position);
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
        });
        holder.ui.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WordShowActivity.class);
                intent.putExtra("playerposition", position);
                editPlayerLauncher.launch(intent);
            }
        }
        );



    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
