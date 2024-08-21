package fr.wph.incognito.android;

import androidx.recyclerview.widget.RecyclerView;

import fr.wph.incognito.android.databinding.PlayerItemBinding;
import fr.wph.incognito.core.api.Player;

public class PlayerItemHolder extends RecyclerView.ViewHolder  {
    public final PlayerItemBinding ui;

    public PlayerItemHolder(PlayerItemBinding ui) {
        super(ui.getRoot());
        this.ui = ui;
    }

    public void setPlayer(String player){
        ui.playerName.setText(player);
    }


}
