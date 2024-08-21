package fr.wph.incognito.android;

import androidx.recyclerview.widget.RecyclerView;

import fr.wph.incognito.android.databinding.GamePlayerItemBinding;
import fr.wph.incognito.android.databinding.PlayerItemBinding;
import fr.wph.incognito.core.api.Player;

public class GamePlayerItemHolder extends RecyclerView.ViewHolder  {
    public final GamePlayerItemBinding ui;

    public GamePlayerItemHolder(GamePlayerItemBinding ui) {
        super(ui.getRoot());
        this.ui = ui;
    }

    public void setPlayer(Player player){
        ui.playerName.setText(player.name());
    }


}
