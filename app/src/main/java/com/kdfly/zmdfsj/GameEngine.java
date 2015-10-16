package com.kdfly.zmdfsj;

import android.content.Context;

import com.kdfly.zmdfsj.model.Player;
import com.kdfly.zmdfsj.model.Store;

/**
 * Created by Sean on 2015-09-08.
 */
public class GameEngine {
    protected static GameEngine instance = new GameEngine();

    protected Store store = new Store();
    protected Player player = new Player();
    protected int[] price = null;

    protected GameEngine(){}

    public static GameEngine getInstance(){
        return instance;
    }
    public void init(Context context){
        price = store.getPrices();
        player.init();
    }
    public int[] getGoodPrices(){
        return store.getPrices();
    }

    public Player getPlayer(){
        return player;
    }
}
