package fr.wph.incognito.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.wph.incognito.android.databinding.PlayerItemBinding;
import fr.wph.incognito.core.api.Player;

public class PlayerItemAdapter extends RecyclerView.Adapter<PlayerItemHolder> {
    private final List<String> playerList;
    private ActivityResultLauncher<Intent> editPlayerLauncher;
    PlayerItemAdapter(List<String> playerList, ActivityResultLauncher<Intent> editPlayerLauncher){
        this.playerList = playerList;
        this.editPlayerLauncher = editPlayerLauncher;
    }
    @NonNull
    @Override
    public PlayerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlayerItemBinding binding = PlayerItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PlayerItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerItemHolder holder, @SuppressLint("RecyclerView") int position) {
        String player = playerList.get(position);
        holder.setPlayer(player);
        holder.ui.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, playerList.size());
            }
        });
        holder.ui.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayerGestionActivity.class);
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
