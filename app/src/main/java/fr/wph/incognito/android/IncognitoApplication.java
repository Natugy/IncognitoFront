package fr.wph.incognito.android;

import android.app.Application;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.wph.incognito.core.api.Game;
import fr.wph.incognito.core.api.Player;

public class IncognitoApplication extends Application {
    List<String> playerList;
    private List<Player> gamePlayerList;
    public List<String> getPlayerList() {
        return playerList;
    }

    public List<Player> getGamePlayerList() {
        return gamePlayerList;
    }

    public void setGamePlayerList(List<Player> gamePlayerList) {
        this.gamePlayerList = gamePlayerList;
    }


    public Game game;
    @Override
    public void onCreate() {
        super.onCreate();
        playerList = new ArrayList<>();
        playerList.add("Batman");
        playerList.add("Superman");
        playerList.add("Wonderwoman");
        playerList.add("Flash");
        WordPickerCSV test = new WordPickerCSV(getAssets());
        game = new Game(test);

    }
    public void editPlayer(String player){
        playerList.remove(player);
        playerList.add(player);
    }
    public void clearPlayerList(){
        playerList.clear();
    }
    public void deletePlayer(int position){
        playerList.remove(position);
    }
}
